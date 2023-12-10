package kr.easw.lesson07.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.easw.lesson07.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 필터 체인 클래스입니다.
 */
@RequiredArgsConstructor
@Component
public class JwtFilterChain extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final JpaUserDetailsService userDetailsService;

    /**
     * 실제 필터링 작업이 이루어지는 메소드입니다.
     *
     * @param request     HTTP 요청 객체
     * @param response    HTTP 응답 객체
     * @param filterChain 필터 체인
     * @throws ServletException 예외
     * @throws IOException      예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 만약 Authorization 헤더가 없다면, 필터 체인을 계속 진행합니다.
        if (request.getHeader("Authorization") == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 토큰을 추출합니다.
        String token = request.getHeader("Authorization");

        // 토큰을 검증합니다.
        switch (jwtService.validate(token)) {
            case VALID:
                // 토큰이 유효하다면, 토큰에서 유저 이름을 추출합니다.
                String userName = jwtService.extractUsername(token);

                // 유저 이름을 통해 유저 정보를 가져옵니다.
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                // 유저 정보를 통해 인증 객체를 생성합니다.
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                ));

                // 인증 성공 로그를 출력합니다.
                System.out.println("Token validated / Role: " + userDetails.getAuthorities());

                // 필터 체인을 계속 진행합니다.
                filterChain.doFilter(request, response);
                return;

            case EXPIRED:
                // 만료된 토큰일 경우 401 에러를 반환합니다.
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired token");
                break;

            case UNSUPPORTED:
                // 지원되지 않는 토큰일 경우 401 에러를 반환합니다.
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unsupported token");
                break;

            case INVALID:
                // 유효하지 않은 토큰일 경우 401 에러를 반환합니다.
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                break;
        }

        // 토큰이 유효하지 않은 경우 로그를 출력합니다.
        System.out.println("Token invalid");
        System.out.println(jwtService.validate(token));
    }
}

package kr.easw.lesson07.configurations;

import kr.easw.lesson07.Constants;
import kr.easw.lesson07.auth.JwtFilterChain;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 스프링 웹 시큐리티를 활성화하고, 필요한 설정을 하는 클래스입니다.
 */
@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SpringSecurityConfiguration {
    // 어플리케이션에 사용할 비밀번호 인코더를 미리 생성합니다.
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final JwtFilterChain filterChain;

    /**
     * HttpSecurity를 구성하는 메서드입니다.
     *
     * @param security HttpSecurity 객체
     * @return SecurityFilterChain 객체
     */
    @Bean
    @SneakyThrows
    SecurityFilterChain configureHttpSecurity(HttpSecurity security) {
        security
                // CSRF 보호를 비활성화합니다. 활성화 시 페이지 내 API 호출이 실패할 수 있습니다.
                .csrf(csrf -> csrf.disable())
                .cors(AbstractHttpConfigurer::disable)
                // 모든 요청에 대해 인증을 요구합니다.
                .authorizeHttpRequests(registry -> {

                    registry.requestMatchers("/dashboard").hasAnyAuthority(Constants.AUTHORITY_ADMIN, Constants.AUTHORITY_GUEST)

                            .requestMatchers("/admin", "/management").hasAnyAuthority(Constants.AUTHORITY_ADMIN)

                            .requestMatchers("/api/v1/data/admin/**").hasAnyAuthority(Constants.AUTHORITY_ADMIN)

                            .requestMatchers("/api/v1/data/**").hasAnyAuthority(Constants.AUTHORITY_ADMIN, Constants.AUTHORITY_GUEST)

                            .requestMatchers("/api/v1/user/**").hasAnyAuthority(Constants.AUTHORITY_ADMIN)

                            .requestMatchers("/api/v1/auth/**").permitAll()

                            .anyRequest().permitAll();
                })

                .logout(customizer -> {
                    customizer.logoutUrl("/logout");
                    customizer.logoutSuccessUrl("/?logout=true");
                })

                .formLogin(customizer -> {
                    customizer
                            // 로그인 페이지를 /login으로 설정합니다.
                            .loginPage("/login")
                            // 로그인 페이지에 대해 모든 사용자가 접근할 수 있도록 설정합니다.
                            .permitAll()
                            // 로그인 성공시 리다이렉트할 페이지를 설정합니다.
                            .defaultSuccessUrl("/dashboard")
                            // 로그인 실패시 리다이렉트할 페이지를 설정합니다.
                            .failureUrl("/login?error=true");
                })

                .addFilterBefore(filterChain, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

    /**
     * BCryptPasswordEncoder 빈을 생성하는 메서드입니다.
     *
     * @return BCryptPasswordEncoder 객체
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        // 사용되는 비밀번호 인코더를 BCrypt로 설정합니다.
        return encoder;
    }

}

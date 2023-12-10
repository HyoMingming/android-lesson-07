package kr.easw.lesson07.controller;

import kr.easw.lesson07.model.dto.TextDataDto;
import kr.easw.lesson07.service.TextDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * REST 컨트롤러로 사용되는 AdminDataRestEndpoint 클래스입니다.
 */
@RestController
@RequestMapping("/api/v1/data/admin")
@RequiredArgsConstructor
public class AdminDataRestEndpoint {
    private final TextDataService textDataService;

    /**
     * 텍스트 데이터를 추가하는 메서드입니다.
     *
     * @param text 추가할 텍스트
     * @return ModelAndView 객체
     */
    @PostMapping("/add")
    public ModelAndView addText(@RequestParam("text") String text) {
        // 텍스트 데이터를 추가합니다.
        textDataService.addText(new TextDataDto(0L, text));

        // 성공 시, 관리자 페이지로 리다이렉트합니다.
        return new ModelAndView("redirect:/admin?success=true");
    }
}

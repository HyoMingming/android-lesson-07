package kr.easw.lesson07.controller;
import kr.easw.lesson07.model.dto.TextDataDto;
import kr.easw.lesson07.service.TextDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/v1/data")

@RequiredArgsConstructor
public class DataRestEndpoint {
    private final TextDataService textDataService;

    @GetMapping("/list")
    public List<TextDataDto> listText() {
        return textDataService.listText();
    }
}

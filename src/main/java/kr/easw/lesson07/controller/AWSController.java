package kr.easw.lesson07.controller;

import kr.easw.lesson07.model.dto.AWSKeyDto;
import kr.easw.lesson07.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * AWS와 관련된 작업을 처리하는 REST 컨트롤러 클래스입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rest/aws")
public class AWSController {
    private final AWSService awsService;

    /**
     * AWS 인증을 처리하는 메서드입니다.
     *
     * @param awsKey AWSKeyDto 객체
     * @return ModelAndView 객체
     */
    @PostMapping("/auth")
    private ModelAndView onAuth(AWSKeyDto awsKey) {
        try {
            awsService.initAWSAPI(awsKey);
            return new ModelAndView("redirect:/");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("redirect:/server-error?errorStatus=" + ex.getMessage());
        }
    }

    /**
     * AWS에 업로드된 파일 목록을 가져오는 메서드입니다.
     *
     * @return 파일 목록
     */
    @GetMapping("/list")
    private List<String> onFileList() {
        System.out.println(awsService.getFileList());
        System.out.println(awsService.getFileInfoList());
        return awsService.getFileList();
    }

    /**
     * AWS에 파일을 업로드하는 메서드입니다.
     *
     * @param file 업로드할 파일
     * @return ModelAndView 객체
     */
    @PostMapping("/upload")
    private ModelAndView onUpload(@RequestParam MultipartFile file) {
        try {
            awsService.upload(file);
            return new ModelAndView("redirect:/admin?success=true");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("redirect:/server-error?errorStatus=" + ex.getMessage());
        }
    }

    /**
     * AWS에서 파일을 다운로드하는 메서드입니다.
     *
     * @param fileName 다운로드할 파일명
     * @return ModelAndView 객체
     */
    @PostMapping("/download")
    private ModelAndView onDownload(@RequestParam String fileName) {
        try {
            // 파일 다운로드 로직 또는 서비스를 통한 다운로드 호출을 구현하세요.
            System.out.format("Passed KEY: %s", fileName);
            awsService.download(fileName);
            return new ModelAndView("redirect:/download");
        } catch (Throwable e) {
            return new ModelAndView("redirect:/server-error?errorStatus=" + e.getMessage());
        }
    }

    /**
     * AWS에 업로드된 파일의 정보 목록을 가져오는 메서드입니다.
     *
     * @return 파일 정보 목록
     */
    @GetMapping("/info")
    private List<String> onFileInfoList() {
        System.out.println(awsService.getFileInfoList());
        return awsService.getFileInfoList();
    }
}

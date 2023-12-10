package kr.easw.lesson07.controller;
import kr.easw.lesson07.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 이 클래스를 컨트롤러로 선언합니다.
@Controller
@RequiredArgsConstructor
public class BaseWebController {
    private final AWSService awsController;
    @RequestMapping("/dashboard")
    public ModelAndView onUserDashboard() {
        return new ModelAndView("user_dashboard.html");
    }

    @RequestMapping("/login")
    public ModelAndView onLogin() {
        return new ModelAndView("login.html");
    }


    @RequestMapping("/register")
    public ModelAndView onRegister() {
        return new ModelAndView("register.html");
    }

    @RequestMapping("/admin")
    public ModelAndView onAdminDashboard() {
        return new ModelAndView("admin_dashboard.html");
    }

    @RequestMapping("/management")
    public ModelAndView onManagementDashboard() {
        return new ModelAndView("management.html");
    }


    @RequestMapping("/")
    public ModelAndView onIndex() {
        if (awsController.isInitialized()) {
            //return new ModelAndView("upload.html");
            //auth 후 최상위 url 에서 upload.html 에서 업로드시 에러 수정
            return new ModelAndView("redirect:/index");
        }
        return new ModelAndView("request_aws_key.html");
    }

    @RequestMapping("/server-error")
    public ModelAndView onErrorTest() {
        return new ModelAndView("error.html");
    }


    @RequestMapping("/upload")
    public ModelAndView onUpload() {
        if (awsController.isInitialized()) {
            return new ModelAndView("upload.html");
        }
        return new ModelAndView("request_aws_key.html");
    }

    @RequestMapping("/download")
    public ModelAndView onDownload() {
        if (awsController.isInitialized()) {
            return new ModelAndView("download.html");
        }
        return new ModelAndView("request_aws_key.html");
    }
    @RequestMapping("/index")
    public ModelAndView onLoginAndRegister() {
        if (awsController.isInitialized()) {
            return new ModelAndView("index.html");
        }
        return new ModelAndView("request_aws_key.html");
    }

}




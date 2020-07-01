package com.phorm.kStart.kontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StrangeController implements ErrorController {
	HttpServletRequest memberRequest;
    @GetMapping("/error")
    public String displayErrorPage(HttpServletRequest httpRequest, Model model) {
         
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
        memberRequest=httpRequest;
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "You missed some information. Please try again.";
                break;
            }
            case 401: {
                errorMsg = "You are not authorized to view this page.";
                break;
            }
            case 404: {
                errorMsg = "Sorry for the inconvenience. Either content is removed or moved to some other place.";
                break;
            }
            default: {
                errorMsg = "Sorry for the inconvenience. Technical team has been notified. Please try after sometime.";
          
            }
            
        }
        model.addAttribute("errorMsg", errorMsg);
        return "error";
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }

	@Override
	public String getErrorPath() {
		return (String) memberRequest
		          .getAttribute("javax.servlet.error.message");
	}
}

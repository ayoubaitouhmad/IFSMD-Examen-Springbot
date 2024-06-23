package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Controller
public class AppErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public Object handleError(HttpServletRequest request, Model model) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(servletWebRequest, ErrorAttributeOptions.defaults());
        HttpStatus status = getStatus(request);

        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            return new ResponseEntity<>(errorAttributesMap, status);
        } else {

           switch (String.valueOf(errorAttributesMap.get("status"))){
               case "404":
                   return "errors/404";
               case "403":
                   return "errors/403";
               default:
                   model.addAttribute("timestamp", errorAttributesMap.get("timestamp"));
                   model.addAttribute("status", errorAttributesMap.get("status"));
                   model.addAttribute("error", errorAttributesMap.get("error"));
                   model.addAttribute("message", errorAttributesMap.get("message"));
                   model.addAttribute("path", errorAttributesMap.get("path"));
                   return "errors/error"; // The name of the Thymeleaf template for error pages
           }


        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

package com.example.ibank.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController{
	
	@RequestMapping("/error")
    public String handleError(Authentication authentication) {
    	return "error"; // 该资源位于resources/static目录下
    }

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}

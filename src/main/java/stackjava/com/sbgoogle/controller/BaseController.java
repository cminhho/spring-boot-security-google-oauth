package stackjava.com.sbgoogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import stackjava.com.sbgoogle.security.GoogleUtils;

@Controller
public class BaseController {
	
	@Autowired
	private GoogleUtils googleUtils;

	@RequestMapping(value = { "/", "/login" })
	public String login() {
		return "login";
	}

	@RequestMapping("/user")
	public String user() {
		return "user";
	}

	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "403";
	}
}

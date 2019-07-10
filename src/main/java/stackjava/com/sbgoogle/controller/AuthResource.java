package stackjava.com.sbgoogle.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import stackjava.com.sbgoogle.common.GooglePojo;
import stackjava.com.sbgoogle.security.GoogleUtils;

@Controller
@RequestMapping("/api")
public class AuthResource {
	
	@Autowired
	private GoogleUtils googleUtils;

	@RequestMapping("/login-google")
	public String loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");
		
		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}

		String accessToken = googleUtils.getToken(code);
		
		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
		UserDetails userDetail = googleUtils.buildUser(googlePojo);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
				userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/user";
	}
}

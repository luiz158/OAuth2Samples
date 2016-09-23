package demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@RequestMapping("/protected")
	public String protectd(HttpServletRequest req) {

        OAuth2Utils.logJWTClaims(req.getParameter("access_token"));
		return "this is a protected OAuth2 resource";
	}

	@RequestMapping("/unprotected")
	public String unprotectd() {
		return "this is not protected";
	}
	
}

package com.ecrops.captcha;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class CaptchaDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, CaptchaDetails> {

	@Override
	public CaptchaDetails buildDetails(HttpServletRequest context) {
		return new CaptchaDetails(context);
	}

}
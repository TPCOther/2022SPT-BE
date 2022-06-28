package com.easyorder.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static Boolean checkVerifyCode(HttpServletRequest request) {
		String strRight = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String strActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if (strActual == null || !strRight.equalsIgnoreCase(strActual)) {
			return false;
		} else
			return true;
	}
}
package com.easyorder.util;

import javax.servlet.http.HttpServletRequest;

//处理Http请求中的一些转换
public class HttpServletRequestUtil {
	public static Integer getInt(HttpServletRequest request, String str) {
		try {
			return Integer.decode(request.getParameter(str));
		} catch (Exception e) {
			return null;
		}
	}

	public static Long getLong(HttpServletRequest request, String str) {
		try {
			return Long.valueOf(request.getParameter(str));
		} catch (Exception e) {
			return null;
		}
	}

	public static Double getDouble(HttpServletRequest request, String str) {
		try {
			return Double.valueOf(request.getParameter(str));
		} catch (Exception e) {
			return null;
		}
	}

	public static Boolean getBoolean(HttpServletRequest request, String str) {
		try {
			return Boolean.valueOf(request.getParameter(str));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getString(HttpServletRequest request, String str) {
		try {
			String s = request.getParameter(str);
			if (s != null) {
				s = s.trim();
			}
			if (s.equals("")) {
				s = null;
			}
			return s;
		} catch (Exception e) {
			return null;
		}
	}
}
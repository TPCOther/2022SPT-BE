package com.easyorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 路径映射
 * @author MSIK
 *
 */
@Configuration
public class FilePathConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**") // 虚拟url路径
				.addResourceLocations("file:E:/EasyOrder/image/upload/"); // 真实本地路径
	}
}

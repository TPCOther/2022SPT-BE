package com.easyorder.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RegisterForm {
	@NotBlank(message = "微信临时授权为空")
	private String code;
	
	@NotBlank(message = "昵称为空")
	private String nickname;
	
	@NotBlank(message = "头像不能为空")
	private String photo;
}

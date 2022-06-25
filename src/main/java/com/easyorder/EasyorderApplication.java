/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 16:47:14
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-06-25 10:01:53
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\EasyorderApplication.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;

@SpringBootApplication
@MapperScan("com.easyorder.mapper")
@EnableMPP
public class EasyorderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyorderApplication.class, args);
	}

}

/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 16:47:14
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-25 11:24:22
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\Controller.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
public class Controller {
    @GetMapping("/hello")
    public String hello(String hello,String l) {
        return "Hello "+hello+"World!"+l;
    }
    

}

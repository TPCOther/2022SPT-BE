/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:38:23
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 09:03:47
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\HeadlineController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:38:23
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-06-25 10:15:28
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\HeadlineController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.easyorder.service.HeadlineService;
import com.easyorder.util.RBody;
import com.google.gson.Gson;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Headline;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/headline")
public class HeadlineController {
    @Resource
    HeadlineService headlineService;
    Gson gson=new Gson();
    
    @PostMapping("/select")
    public RBody selectHeadline(@RequestBody Headline headline)
    {
        RBody rBody=new RBody();
        BaseExecution<Headline> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.headlineService.selectHeadlineList(headline);
            rBody=RBody.ok().data(baseExecution.getTList());
        } catch (Exception e) {
            
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/update")
    public RBody updateHeadline(@RequestBody Headline headline)
    {
        RBody rBody=new RBody();
        //BaseExecution<Headline> baseExecution=new BaseExecution<>();
        try {
            //baseExecuion=
            headlineService.updateHeadline(headline);
            rBody=RBody.ok();
        } catch (Exception e) {
            
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/insert")
    public RBody insertHeadline(@RequestBody Headline headline)
    {
        RBody rBody=new RBody();
        BaseExecution<Headline> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.headlineService.insertHeadline(headline);
            rBody=RBody.ok().data(baseExecution.getTemp().getHeadlineId());
        } catch (Exception e) {
            
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/delete")
    public RBody deleteHeadline(@RequestBody Headline headline)
    {
        RBody rBody=new RBody();
        //BaseExecution<Headline> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            headlineService.deleteHeadline(headline);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody ;
       }
}


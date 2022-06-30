/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:38:23
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 15:16:14
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;



import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import com.easyorder.service.HeadlineService;
import com.easyorder.util.CodeUtil;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;
import com.google.gson.Gson;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Headline;
import com.easyorder.enums.ExecuteStateEum;

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
        
        BaseExecution<Headline> baseExecution=new BaseExecution<>();
        RBody rBody=new RBody();
        try {
            baseExecution=this.headlineService.selectHeadlineList(headline);
            rBody=RBody.ok().data(baseExecution.getTList());
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }  
        return rBody;
    }

    @PostMapping("/update")
    @ResponseBody
    public RBody updateHeadline(HttpServletRequest request)
    {
        String headlineString=HttpServletRequestUtil.getString(request, "headlineStr");
        Headline headline=null;
        try {
            headline=gson.fromJson(headlineString, Headline.class);
        } catch (Exception e) {
            return RBody.error(e.getMessage());
        }
        MultipartHttpServletRequest multipartHttpServletRequest;
        CommonsMultipartFile headlineImg=null;
        //Map<CommonsMultipartFile,String> headlineImgMap=new HashMap<>();
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if(commonsMultipartResolver.isMultipart(request))
            {
                multipartHttpServletRequest=(MultipartHttpServletRequest)request;
                headlineImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("headlineImg");
            }
        } catch (Exception e) {
            return RBody.error("上传失败："+e.getMessage());
        }
        if(headline!=null&&headline.getHeadlineId()!=null)
        {
            try {
                BaseExecution<Headline> baseExecution=headlineService.updateHeadline(headline,headlineImg);
            if(baseExecution.getEum().getState()==ExecuteStateEum.SUCCESS.getState())
            {
                return RBody.ok(baseExecution.getEum().getStateInfo());
            }
            } catch (Exception e) {
                return RBody.error(e.getMessage());
            }
        }
        return RBody.error(ExecuteStateEum.INCOMPLETE.getStateInfo());
    }



    @PostMapping("/insert")
    @ResponseBody
    public RBody insertHeadline(HttpServletRequest request)
    {
        //验证码
//        if(!CodeUtil.checkVerifyCode(request))
//        {
//            return RBody.error("验证码错误!");
//        }
        //接受消息
        String headlineString=HttpServletRequestUtil.getString(request,"headlineStr");
        Headline headline=null;
        //将信息转化为实体类实例
        try {
            headline=gson.fromJson(headlineString,Headline.class);
        } catch (Exception e) {
            return RBody.error(e.getMessage());
        }

        //处理图片数据流
        MultipartHttpServletRequest multipartHttpServletRequest;
        CommonsMultipartFile headlineImg=null;
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //判断文件流
            if(commonsMultipartResolver.isMultipart(request))
            {
                multipartHttpServletRequest=(MultipartHttpServletRequest)request;
                headlineImg =(CommonsMultipartFile)multipartHttpServletRequest.getFile("headlineImg");
                if(headlineImg==null)
                {
                    return RBody.error("上传失败，图片文件为空");
                }
            }
        }catch (Exception e) {
            return RBody.error("上传失败:" + e.getMessage());
        }
        if(headline!=null)
        {
            try {
                BaseExecution<Headline> baseExecution=headlineService.insertHeadline(headline,headlineImg);
                if(baseExecution.getEum().getState()==ExecuteStateEum.SUCCESS.getState())
                {
                    return RBody.ok("添加成功!");
                }else{
                    return RBody.error(baseExecution.getEum().getStateInfo());
                }
            } catch (Exception e) {
                return RBody.error(e.getMessage());
            }
        }
        return RBody.error();
    }

    @PostMapping("/delete")
    public RBody deleteHeadline(@RequestBody Headline headline)
    {
        if(headline!=null&&headline.getHeadlineId()!=null)
        {
            BaseExecution<Headline> baseExecution=headlineService.deleteHeadline(headline);
            if(baseExecution.getEum().getState()==ExecuteStateEum.SUCCESS.getState())
            {
                RBody rBody=new RBody();
                rBody=RBody.ok(baseExecution.getEum().getStateInfo());
                return rBody;
            }else{
                return RBody.error(baseExecution.getEum().getStateInfo());
            }
        }
        return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
    }


}


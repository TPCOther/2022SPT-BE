/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:31:56
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-06-25 10:31:48
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\HeadlineServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.easyorder.mapper.HeadlineMapper;
import com.easyorder.service.HeadlineService;



import org.springframework.stereotype.Service;
import com.easyorder.entity.Headline;
@Service
public class HeadlineServiceImpl implements HeadlineService{
    
    @Resource
    HeadlineMapper headlineMapper;
    
    @Override
    public List<Headline> getHeadlineList() {
        // TODO Auto-generated method stub
        List<Headline> headlines=headlineMapper.selectList(null);
        return headlines;
    }

    @Override
    public void updateHeadlineList(Headline updateHeadline) {
        // TODO Auto-generated method stub
        headlineMapper.updateById(updateHeadline);
    }
    
    
    
    
    
    
}

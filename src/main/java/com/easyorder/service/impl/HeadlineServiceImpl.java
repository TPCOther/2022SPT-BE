/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:31:56
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-25 17:27:55
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\HeadlineServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.easyorder.mapper.HeadlineMapper;
import com.easyorder.service.HeadlineService;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.entity.Headline;
@Service
public class HeadlineServiceImpl implements HeadlineService{
    
    @Resource
    HeadlineMapper headlineMapper;
    
    Headline headline;
    @Override
    public List<Headline> getHeadlineList(Headline headline) {
        // TODO Auto-generated method stub

        QueryWrapper<Headline> wrapper=new QueryWrapper<>();
        Long id=headline.getHeadlineId();
        String name=headline.getHeadlineName();
        String link=headline.getHeadlineLink();
        String img=headline.getHeadlineImg();
        Integer state=headline.getHeadlineState();
        Integer priority=headline.getPriority();
        String createTime=headline.getCreateTime();
        String lastEditTime=headline.getLastEditTime();
        
        wrapper.eq(null!=id, "headline_id",id);
        wrapper.eq(StringUtils.isNotEmpty(name),"headline_name",name);
        wrapper.eq(null!=link, "headline_link",link);
        wrapper.eq(null!=img, "headline_img",img);
        wrapper.eq(null!=state, "headline_state",state);
        wrapper.eq(null!=priority, "priority",priority);
        wrapper.eq(null!=createTime, "create_time",createTime);
        wrapper.eq(null!=lastEditTime, "last_edit_time",lastEditTime);
        

        List<Headline> headlines=headlineMapper.selectList(wrapper);
        return headlines;
    }

    @Transactional
    @Override
    public void updateHeadline(Headline headline) {
        // TODO Auto-generated method stub
        if(headline.getHeadlineId()!=null)
        {
            headlineMapper.updateById(headline);
        }
        
    }

    @Override
    public void insertHeadline(Headline headline) {
        // TODO Auto-generated method stub
        if(headline.getHeadlineId()!=null)
        {
            headlineMapper.insert(headline);
        }
    }

    @Override
    public void deleteHeadline(Headline headline) {
        // TODO Auto-generated method stub
        if(headline.getHeadlineId()!=null)
        {
            headlineMapper.deleteById(headline);
        }
    }

    
    
    
    
    
    
    
}

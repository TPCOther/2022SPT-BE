/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:31:56
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 09:34:03
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\HeadlineServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.easyorder.mapper.HeadlineMapper;
import com.easyorder.service.HeadlineService;
import com.easyorder.util.BaseExecuteException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Headline;
import com.easyorder.enums.ExecuteStateEum;
@Service
public class HeadlineServiceImpl implements HeadlineService{
    
    @Resource
    HeadlineMapper headlineMapper;
    
    
    @Override
    public BaseExecution<Headline> selectHeadlineList(Headline headline) throws BaseExecuteException{

        BaseExecution<Headline> baseExecution = new BaseExecution<Headline>();
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
        wrapper.eq(StringUtils.isNotEmpty(link), "headline_link",link);
        wrapper.eq(StringUtils.isNotEmpty(img), "headline_img",img);
        wrapper.eq(null!=state, "headline_state",state);
        wrapper.eq(null!=priority, "priority",priority);
        wrapper.eq(StringUtils.isNotEmpty(createTime), "create_time",createTime);
        wrapper.eq(StringUtils.isNotEmpty(lastEditTime), "last_edit_time",lastEditTime);
        
        try {
            List<Headline> headlineList=headlineMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(headlineList);
            baseExecution.setCount(Long.valueOf(headlineList.size()));
            return baseExecution;
        } catch (Exception e) {
            
            throw new BaseExecuteException("查询头条(Headline)失败:"+e.getMessage());
        }
        
    }

    @Transactional
    @Override
    public BaseExecution<Headline> updateHeadline(Headline headline) throws BaseExecuteException{
        
        BaseExecution<Headline> baseExecution=new BaseExecution<>();
        try {
            int effctedNum=headlineMapper.updateById(headline);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("更新0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(headline);
            return baseExecution;
        } catch (Exception e) {
            
            throw new BaseExecuteException("更新头条(Headline)失败:"+e.getMessage());
        }
        
    }

    @Transactional
    @Override
    public BaseExecution<Headline> insertHeadline(Headline headline) throws BaseExecuteException{
        
        BaseExecution<Headline> baseExecution=new BaseExecution<Headline>();
        try {
            if(headline.getHeadlineId()!=null)
            {
                headline.setCreateTime(new Date());
                headline.setLastEditTime(new Date());
                if(headline.getPriority()==null)
                {
                    headline.setPriority(1);
                }
                if(headline.getHeadlineState()==null)
                {
                    headline.setHeadlineState(0);
                }
            }
            int effctedNum=headlineMapper.insert(headline);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("插入0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(headline);
            return baseExecution;
        } catch (Exception e) {
            
            throw new BaseExecuteException("创建头条(Headline)失败:"+e.getMessage());
        }
    }

    @Transactional
    @Override
    public BaseExecution<Headline> deleteHeadline(Headline headline) throws BaseExecuteException{
        
        BaseExecution<Headline> baseExecution = new BaseExecution<>();
         try{
             int effctedNum = headlineMapper.deleteById(headline);
             if(effctedNum <= 0){
                 throw new BaseExecuteException("删除0条数据");
             }
             baseExecution.setEum(ExecuteStateEum.SUCCESS);
             baseExecution.setTemp(headline);
             return baseExecution;
            
         }catch(Exception e){       
             throw new BaseExecuteException("删除头条(Headline)失败: "+e.getMessage());
         }
    }
  
}

/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:11:02
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 10:16:28
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\entity\Headline.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("headline")
public class Headline{
    @TableId(type = IdType.AUTO)
    private Long headlineId;
    private String headlineName;
    private String headlineLink;
    private String headlineImg;
    private Integer headlineState;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;

    public Headline()
    {
        
    }

    
}
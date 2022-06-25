/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:30:36
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-25 17:27:24
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\HeadlineService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;

import java.util.List;

import com.easyorder.entity.Headline;



public interface HeadlineService {
    List<Headline> getHeadlineList(Headline headline);
    void updateHeadline(Headline headline);
    void insertHeadline(Headline headline);
    void deleteHeadline(Headline headline);
}


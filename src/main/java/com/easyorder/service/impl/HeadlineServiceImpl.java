/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 17:31:56
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 11:03:49
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
import com.easyorder.util.ImageUtil;
import com.easyorder.util.PathUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Headline;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.enums.HeadlineEum;

@Service
public class HeadlineServiceImpl implements HeadlineService {

	@Resource
	HeadlineMapper headlineMapper;

	@Override
	public BaseExecution<Headline> selectHeadlineList(Headline headline) throws BaseExecuteException {

		BaseExecution<Headline> baseExecution = new BaseExecution<Headline>();
		QueryWrapper<Headline> wrapper = new QueryWrapper<>();
		Long id = headline.getHeadlineId();
		String name = headline.getHeadlineName();
		String link = headline.getHeadlineLink();
		String img = headline.getHeadlineImg();
		Integer state = headline.getHeadlineState();
		Integer priority = headline.getPriority();
		Date createTime = headline.getCreateTime();
		Date lastEditTime = headline.getLastEditTime();

		wrapper.eq(null != id, "headline_id", id);
		wrapper.eq(StringUtils.isNotEmpty(name), "headline_name", name);
		wrapper.eq(StringUtils.isNotEmpty(link), "headline_link", link);
		wrapper.eq(StringUtils.isNotEmpty(img), "headline_img", img);
		wrapper.eq(null != state, "headline_state", state);
		wrapper.eq(null != priority, "priority", priority);
		wrapper.eq(createTime != null, "create_time", createTime);
		wrapper.eq(lastEditTime != null, "last_edit_time", lastEditTime);

		try {
			List<Headline> headlineList = headlineMapper.selectList(wrapper);
			baseExecution.setEum(ExecuteStateEum.SUCCESS);
			baseExecution.setTList(headlineList);
			baseExecution.setCount(Long.valueOf(headlineList.size()));
			return baseExecution;
		} catch (Exception e) {

			throw new BaseExecuteException("查询头条(Headline)失败:" + e.getMessage());
		}

	}

	@Transactional
	@Override
	public BaseExecution<Headline> updateHeadline(Headline headline, CommonsMultipartFile headlineImg)
			throws BaseExecuteException {

		headline.setLastEditTime(new Date());
		if (headlineImg!= null) {
			Headline temp = headlineMapper.selectById(headline.getHeadlineId());
			if (temp.getHeadlineImg() != null) {
				ImageUtil.deleteFile(temp.getHeadlineImg());
			}
			addHeadlineImg(headline, headlineImg);
		}
		try {
			int e = headlineMapper.updateById(headline);
			if (e <= 0) {
				throw new BaseExecuteException("更新头条信息失败！");
			}
			return new BaseExecution<Headline>(ExecuteStateEum.SUCCESS);
		} catch (Exception e) {
			return new BaseExecution<Headline>(ExecuteStateEum.INNER_ERROR);
		}
	}

	@Transactional
	@Override
	public BaseExecution<Headline> insertHeadline(Headline headline, CommonsMultipartFile headlineImg)
			throws BaseExecuteException {

		BaseExecution<Headline> baseExecution = new BaseExecution<Headline>();
		if (headline != null) {
			headline.setCreateTime(new Date());
			headline.setLastEditTime(new Date());
			if (headline.getPriority() == null) {
				headline.setPriority(1);
			}
			if (headline.getHeadlineState() == null) {
				headline.setHeadlineState(HeadlineEum.NOTUSING.getState());
			}
			try {
				int effctedNum = headlineMapper.insert(headline);
				if (effctedNum <= 0) {
					throw new BaseExecuteException("插入0条数据");
				}

			} catch (Exception e) {

				throw new BaseExecuteException("创建头条(Headline)失败:" + e.getMessage());
			}

			if (headlineImg != null) {
				try {
					addHeadlineImg(headline, headlineImg);
					int x = headlineMapper.updateById(headline);
					if (x <= 0) {
						throw new BaseExecuteException("图片添加失败!");
					}
				} catch (Exception e) {
					throw new BaseExecuteException(e.getMessage());
				}
			}else {
				return new BaseExecution<Headline>(ExecuteStateEum.INCOMPLETE);
			}
			baseExecution.setEum(ExecuteStateEum.SUCCESS);
			baseExecution.setTemp(headline);
			return baseExecution;
		} else {
			return new BaseExecution<Headline>(ExecuteStateEum.INCOMPLETE);
		}

	}

	@Transactional
	@Override
	public BaseExecution<Headline> deleteHeadline(Headline headline) throws BaseExecuteException {

		if (headline.getHeadlineId() != null) {
			try {
				Headline temp = headlineMapper.selectById(headline.getHeadlineId());
				if (temp.getHeadlineImg() != null) {
					ImageUtil.deleteFile(temp.getHeadlineImg());
				}
				int e = headlineMapper.deleteById(headline);
				if (e <= 0) {
					throw new BaseExecuteException("删除头条失败！");
				}
				return new BaseExecution<Headline>(ExecuteStateEum.SUCCESS);
			} catch (Exception e) {
				return new BaseExecution<Headline>(ExecuteStateEum.INNER_ERROR);
			}
		} else {
			return new BaseExecution<Headline>(ExecuteStateEum.INCOMPLETE);
		}
	}

	private void addHeadlineImg(Headline headline, CommonsMultipartFile headlineImg) {
		String dest = PathUtil.getHeadlineImagePath(headline);
		String addr = ImageUtil.generateThumbnail(headlineImg, dest);
		headline.setHeadlineImg(addr);

	}
}

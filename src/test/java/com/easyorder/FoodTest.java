package com.easyorder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.easyorder.entity.Food;
import com.easyorder.service.FoodService;

public class FoodTest extends BaseTest {
	@Resource
	FoodService fs;
	
	@Test 
	public void foodDelete() {
		System.out.println(fs.deletFoodByFoodId(1L).getEum().getStateInfo());
	}
	
	@Test
	@Ignore
	public void foodSelect() {
		Food f=new Food();
		f.setFoodName("回锅肉");
		List<Food> foodList=fs.selectFoodList(f, 2, 1).getTList();
		for(Food fd:foodList)
			System.out.println(fd);
	}
	@Test
	@Ignore
	public void foodInsertUpdate() {
		File file = new File("D:\\图\\Naruto.jpg");
		FileItem fileItem = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		CommonsMultipartFile foodImg = new CommonsMultipartFile(fileItem);
		Map<CommonsMultipartFile, String> map = new HashMap<>();
		String s = "D:\\图\\Naruto.jpg";
		file = new File(s);
		FileItem fileItem1 = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		foodImg = new CommonsMultipartFile(fileItem1);
		map.put(foodImg, s);

		s = "D:\\图\\csgo.jpg";
		file = new File(s);
		FileItem fileItem2 = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		foodImg = new CommonsMultipartFile(fileItem2);
		map.put(foodImg, s);

		file = new File(s);
		FileItem fileItem3 = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		foodImg = new CommonsMultipartFile(fileItem3);
		map.put(foodImg, s);
		Food f=new Food();
		f.setFoodId(1L);
		f.setFoodName("小炒肉");
		fs.updateFood(f, foodImg, map);

	}
}

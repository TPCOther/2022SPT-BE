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

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Food;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.FoodService;
import com.google.gson.Gson;

public class FoodTest extends BaseTest {
	@Resource
	FoodService fs;
	@Resource
	Gson gson;
	@Test 
	@Ignore
	public void foodDelete() {
		System.out.println(fs.deletFoodByFoodId(1L).getEum().getStateInfo());
	}
	@Test
	@Ignore
	public void foodJson() {
		Food f=new Food();
		f.setFoodName("回锅肉");
		List<Food> foodList=fs.selectFoodList(f, 1, 1).getTList();
		for(Food fd:foodList) {
			System.out.println(fd);
			System.out.println(gson.toJson(fd));
		}
			
		
	}
	@Test
	public void foodSelect() {
		
		BaseExecution<Food> be= fs.selectFoodByFoodId(21l);
		if(be.getEum()==ExecuteStateEum.SUCCESS) {
			System.out.print(be.getTemp());
		}
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

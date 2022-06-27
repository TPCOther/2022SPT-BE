package com.easyorder;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.junit.Test;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.easyorder.entity.Food;
import com.easyorder.enums.FoodStateEum;
import com.easyorder.service.FoodService;

public class FoodTest extends BaseTest {
	@Resource
	FoodService fs;

	@Test
	public void foodInsert() {
		File file = new File("D:\\图\\csgo.jpg");
		FileItem fileItem = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		CommonsMultipartFile foodImg = new CommonsMultipartFile(fileItem);
		Map<CommonsMultipartFile, String> map = new HashMap<>();
		String s = "D:\\图\\csgo.jpg";
		file = new File(s);
		FileItem fileItem1 = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		foodImg = new CommonsMultipartFile(fileItem1);
		map.put(foodImg, s);

		s = "D:\\图\\Naruto.jpg";
		file = new File(s);
		FileItem fileItem2 = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		foodImg = new CommonsMultipartFile(fileItem2);
		map.put(foodImg, s);

		file = new File(s);
		FileItem fileItem3 = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
		foodImg = new CommonsMultipartFile(fileItem3);
		map.put(foodImg, s);

		fs.insertFood(new Food(null, "回锅肉", "test", "test", 12.0f, 11.0f, FoodStateEum.SALING.getState(), "test", 1,
				new Date(), new Date(), (long) 1, null), foodImg, map);

	}
}

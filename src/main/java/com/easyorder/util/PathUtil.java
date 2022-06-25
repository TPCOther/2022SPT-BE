package com.easyorder.util;

import com.easyorder.entity.Food;

/*
 * 管理文件路径
 */
public class PathUtil {
	
	private static String seperator =System.getProperty("file.separator");//获取不同操作系统的分隔符
	
	
	/**
	 * 获取设置好的根路径（所有的文件都保存在"E:/SummerTraining_File/image"文件夹下）
	 * @return
	 */
	public static String getImgBasePath() {
		String os=System.getProperty("os.name");
		String basePath;
		if(os.toLowerCase().startsWith("win")) {
			basePath="E:/SummerTraining_File/image"; 
		}else {
			basePath="/SummerTraining_File/image";
		}
		basePath=basePath.replace("/", seperator);//针对不同系统替换
		return basePath;
	}
	
	/**
	 * 在设置商店的图片保存的相对路径"/upload/item/food/""/"  一般和上面的根路径获得文件的绝对路径
	 * @param 
	 * @return
	 */
	public static String getFoodImagePath(Food food) {
		String productImagePath="/upload/item/food/"+food.getFoodId()+"/product/";
		return productImagePath.replace("/", seperator);
	}
	public static String getFoodImageDetailPath(Food food) {
		String productImagePath="/upload/item/food/"+food.getFoodId()+"/productdetail/";
		return productImagePath.replace("/", seperator);
	}
	public static String getFoodAllImagePath(Food food) {
		String productImagePath="/upload/item/food/"+food.getFoodId()+"/";
		return productImagePath.replace("/", seperator);
	}
}
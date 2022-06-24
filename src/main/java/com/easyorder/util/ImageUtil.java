package com.easyorder.util;

/*
 * 压缩图片处理类
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	// 设置水印图片的所在文件夹
	private static String basePath = PathUtil.getImgBasePath();
	// 设置日期的格式作为文件命的一部分（win10文件命名不能有":"）
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
	// 生成随机数作为文件命的一部分
	private static final Random r = new Random();

	/**
	 * 处理缩略图参数 将文件压缩加水印并保存在targetAddress相对路径下
	 * 
	 * @param thumbnail文件流
	 * @param targetAddress输出文件的相对路径
	 * @return 输出文件的相对路径+文件名
	 */
	public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddress) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddress);
		String relativeAddress = targetAddress + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddress);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(500, 500).outputQuality(0.8f).toFile(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relativeAddress;
	}

	
	public static String clearThumbnail(CommonsMultipartFile thumbnail, String targetAddress) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddress);
		String relativeAddress = targetAddress + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddress);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(1000, 1000)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "Sh3rlock.png")), 0.25f)
					.outputQuality(0.9f).toFile(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relativeAddress;
	}
	/**
	 * 创建目标路径及涉及到的目录,如果没有自动创建
	 * 
	 * @param targetAddress
	 */
	private static void makeDirPath(String targetAddress) {
		// TODO 自动生成的方法存根
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddress;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 根据当前时间+5位随机数生成随机的文件名
	 * 
	 * @return 文件名
	 */
	public static String getRandomFileName() {
		int random = r.nextInt(89999) + 10000;
		String date = simpleDateFormat.format(new Date());
		return date + random;
	}

	/**
	 * 获取文件流的扩展名
	 * 
	 * @param thumbnail 文件流
	 * @return
	 */
	public static String getFileExtension(CommonsMultipartFile thumbnail) {
		String originalFileName = thumbnail.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 如果是路径是文件夹就删除文件夹、如果是文件就只删除文件
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) {
		File filePath = new File(PathUtil.getImgBasePath() + path);
		if (filePath.exists()) {
			//删除文件夹下的所有内容
			if (filePath.isDirectory()) {
				for (File x : filePath.listFiles()) {
					x.delete();
				}
			}
			//删除该文件夹
			filePath.delete();
		}

	}

//	调试代码
//	public static void main(String args[]) {
//		
//	}
}

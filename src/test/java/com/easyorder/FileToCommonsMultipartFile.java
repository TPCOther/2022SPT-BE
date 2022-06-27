package com.easyorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;



public class FileToCommonsMultipartFile {
	//把File转化为CommonsMultipartFile
    public static FileItem createFileItem(File file, String fieldName) {
     //DiskFileItemFactory()：构造一个配置好的该类的实例
     //第一个参数threshold(阈值)：以字节为单位.在该阈值之下的item会被存储在内存中，在该阈值之上的item会被当做文件存储
     //第二个参数data repository：将在其中创建文件的目录.用于配置在创建文件项目时，当文件项目大于临界值时使用的临时文件夹，默认采用系统默认的临时文件路径
     FileItemFactory factory = new DiskFileItemFactory(16, null);
     //fieldName：表单字段的名称；第二个参数 ContentType；第三个参数isFormField；第四个：文件名
     FileItem item = factory.createItem(fieldName, "text/plain", true, file.getName());
     int bytesRead = 0;
     byte[] buffer = new byte[8192];
     FileInputStream fis = null;
     OutputStream os = null;
     try {
      fis = new FileInputStream(file);
      os = item.getOutputStream();
      while((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
       os.write(buffer, 0, bytesRead);//从buffer中得到数据进行写操作
      }
     } catch(IOException e) {
      e.printStackTrace();
     } finally {
      try {
       if(os != null) {
        os.close();
       }
       if(fis != null) {
        fis.close();
       }
      } catch (IOException e) {
       e.printStackTrace();
      }
     }
     return item;
    }
}

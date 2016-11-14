package com.yellowcong.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.yellowcong.utils.JAEUtils;

public class TestJAEUtils {
	
	
	@Test 
	public void testProperties(){
		
		try {
			InputStream in = JAEUtils.class.getClassLoader().getResourceAsStream("config.properties");
			
			Properties prop = new Properties();
			prop.load(in);
			String ak = prop.getProperty("ak");
			String sk=prop.getProperty("sk");
			String bucket = prop.getProperty("bucket");
			
			System.out.println(ak);
			System.out.println(sk);
			System.out.println(bucket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testCreateBucket(){
		JAEUtils.createBucket("xxxxxxxxxx");
	}
	
	@Test
	public void testUpload(){
		String filename = JAEUtils.upload(new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg"));
		System.out.println(filename);
	}
	
	@Test
	public void testUpload4(){
		String filename = JAEUtils.upload(new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg"),"xxxxxxxeee.jpg");
		System.out.println(filename);
	}
	
	@Test
	public void testUpload2(){
		String filename = JAEUtils.upload("xxxxxxxxxx",new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg"));
		System.out.println(filename);
	}
	@Test
	public void testUpload6() throws FileNotFoundException{
		File file = new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg");
		InputStream in =  new FileInputStream(file);
		String filename = JAEUtils.upload("xxxxxxxxxx","xxxxxxxxxxxxxxxx.jpg",in,file.length());
		System.out.println(filename);
	}
	
	@Test
	public void testUpload5() throws FileNotFoundException{
		File file = new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg");
		InputStream in =  new FileInputStream(file);
		String filename = JAEUtils.upload("xxxxxxxxxxxxxxxx.jpg",in,file.length());
		System.out.println(filename);
	}
	@Test
	public void testUpload3(){
		String filename = JAEUtils.upload("xxxxxxxxxx",new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg"),"yellowcong.jpg");
		System.out.println(filename);
	}
	
	/**
	 * 测试获取所有文件
	 */
	@Test
	public void testListAllFIles(){
		List<String> names = JAEUtils.listAllFiles();
		for(String name:names){
			System.out.println(name);
		}
	}
	
	/**
	 * 测试获取所有文件  指定bucket
	 */
	@Test
	public void testListAllFIles2(){
		List<String> names = JAEUtils.listAllFiles("xxxxxxxxxx");
		for(String name:names){
			System.out.println(name);
		}
	}
	
	
	@Test
	public void testDelet(){
		JAEUtils.deleteFile("xxxxxxxeee.jpg");
	}
	
	@Test
	public void testDelet2(){
		JAEUtils.deleteFile("xxxxxxxxxx", "yellowcong.jpg");
	}
	
	/**
	 * 删除有数据 的bucket
	 */
	@Test
	public void testDeletBucket(){
		JAEUtils.deleteBucket("xxxxxxxxxx");
	}

	@Test
	public void testClearAllFiles(){
		JAEUtils.clearAllFiles("xxxxxxxxxx");
	}
	
	@Test
	public void testClearAllFiles2(){
		JAEUtils.clearAllFiles();
	}
	
	
}

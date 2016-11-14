package com.yellowcong.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jcloud.jss.Credential;
import com.jcloud.jss.JingdongStorageService;
import com.jcloud.jss.domain.ObjectListing;
import com.jcloud.jss.domain.ObjectSummary;
import com.jcloud.jss.service.BucketService;

/**
 * 创建一个JAE云的工具包
 * 
 * @author 狂飙のyellowcong 2015年3月29日
 * 
 */
public class JAEUtils {
	// 私有化够着方法，不可以实例化
	private JAEUtils() {
	}

	private static JingdongStorageService jss = null;
	private static String ak;
	private static String sk;
	private static String bucket;
	private static Credential credential;

	static {
		try {
			// 获取Property中的参数
			InputStream in = JAEUtils.class.getClassLoader()
					.getResourceAsStream("config.properties");

			Properties prop = new Properties();
			prop.load(in);
			ak = prop.getProperty("ak");
			sk = prop.getProperty("sk");
			bucket = prop.getProperty("bucket");

			credential = new Credential(ak, sk);
			jss = new JingdongStorageService(credential);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获取我们的JJS 对象
	 * 
	 * @return
	 */
	public static JingdongStorageService getJJS() {
		// 当没有的时候，我们 会重新创建一个对象
		if (jss == null) {
			jss = new JingdongStorageService(credential);
		}
		return jss;
	}

	/**
	 * 创建公共bucket对象
	 * 
	 * @param bucketName
	 */
	public static void createBucket(String bucketName) {
		JingdongStorageService jss = JAEUtils.getJJS();
		jss.createBucket(bucketName);
		jss.bucket(bucketName).acl().allowAnyoneRead().set();
		// 关闭资源
		jss.destroy();
	}

	/**
	 * 这个方法没啥用出
	 * 
	 * @param bucketName
	 */
	public static void createPrimaryBucket(String bucketName) {
		JingdongStorageService jss = JAEUtils.getJJS();
		jss.createBucket(bucketName);
		// 关闭资源
		jss.destroy();
	}

	/**
	 * 删除bucket对象
	 * 
	 * @param bucketName
	 */
	public static void deleteBucket(String bucketName) {
		JingdongStorageService jss = JAEUtils.getJJS();
		// 获取所有的对象
		ObjectListing objs = jss.bucket(bucketName).listObject();
		if (objs.getObjectSummaries().size() > 0) {
			throw new RuntimeException("bucket 中还有内容");
		} else {
			jss.bucket(bucketName).delete();
		}
		jss.destroy();
	}

	/**
	 * 获取Buckent 对象
	 * 
	 * @param bucketName
	 * @return
	 */
	public static BucketService getBucket(String bucketName) {
		return JAEUtils.getJJS().bucket(bucketName);
	}

	/**
	 * 获取到默认的 Property 中配置的bucket
	 * 
	 * @return
	 */
	public static BucketService getBucket() {
		return JAEUtils.getJJS().bucket(bucket);
	}

	/**
	 * 获取所有的bucket对象
	 * 
	 * @param bucket
	 * @return
	 */
	public static List<String> listAllFiles(String bucket) {
		BucketService buck = JAEUtils.getBucket(bucket);
		// 获取所有的对象
		ObjectListing objs = buck.listObject();
		List<ObjectSummary> sumys = objs.getObjectSummaries();
		List<String> names = null;
		if (sumys.size() > 0) {
			names =new ArrayList<String>();
			// 删除所有的 文件
			for (ObjectSummary obj : sumys) {
				String fileName = obj.getKey();
				names.add(fileName);
			}
		}
		return names;
	}
	
	/**
	 * 获取所有的bucket对象 默认bucket
	 * @param bucket
	 * @return
	 */
	public static List<String> listAllFiles() {
		BucketService buck = JAEUtils.getBucket(bucket);
		// 获取所有的对象
		ObjectListing objs = buck.listObject();
		List<ObjectSummary> sumys = objs.getObjectSummaries();
		List<String> names = null;
		if (sumys.size() > 0) {
			names =new ArrayList<String>();
			// 删除所有的 文件
			for (ObjectSummary obj : sumys) {
				String fileName = obj.getKey();
				names.add(fileName);
			}
		}
		return names;
	}


	/**
	 * 上传数据，返回调用路径 默认bucket中
	 * 
	 * @param file
	 * @return
	 */
	public static String upload(File file) {
		String key =UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(file.getName());
		JAEUtils.getBucket().object(key).entity(file).put();
		JAEUtils.closeJJS(jss);
		return "http://storage.jcloud.com/" + bucket + "/" + key;
	}
	
	/**
	 * 上传到指定的bucket对象中
	 * @param bucketName
	 * @param file
	 * @return
	 */
	public static String upload(String bucketName,File file) {
		String key =UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(file.getName());
		JAEUtils.getBucket(bucketName).object(key).entity(file).put();
		JAEUtils.closeJJS(jss);
		return "http://storage.jcloud.com/" + bucketName + "/" + key;
	}
	
	/**
	 * 上传数据，返回调用路径
	 * 
	 * @param file
	 * @param filename
	 * @return
	 */
	public static String upload(File file,String filename) {
		JAEUtils.getBucket().object(filename).entity(file).put();
		JAEUtils.closeJJS(jss);
		return "http://storage.jcloud.com/" + bucket + "/" + filename;
	}
	/**
	 * 通过MultipartFile上传 传递 到默认的地址 ， 自动生成 图片地址  
	 * 多用于CKeditor中文件添加
	 * @param file
	 * @return
	 */
	public static String upload(MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			InputStream in = file.getInputStream();
			long size = file.getSize();
			String key =UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
			JAEUtils.getBucket().object(key).entity(size,in).put();
			JAEUtils.closeJJS(jss);
			return "http://storage.jcloud.com/" + bucket + "/" + key;
		} catch (Exception e) {
			throw new RuntimeException("JAE文件添加失败");
		}
	}
	
	/**
	 * 通过MultipartFile上传 ，设定 名称， 这个方法多用于图片上传中 和首页图片操作
	 * @param file
	 * @param newName
	 * @return
	 */
	public static String upload(MultipartFile file,String newName) {
		try {
			InputStream in = file.getInputStream();
			long size = file.getSize();
			JAEUtils.getBucket().object(newName).entity(size,in).put();
			JAEUtils.closeJJS(jss);
			return "http://storage.jcloud.com/" + bucket + "/" + newName;
		} catch (Exception e) {
			throw new RuntimeException("JAE文件添加失败");
		}
	}
	/**
	 * 通过MultipartFile上传
	 * @param file
	 * @return
	 */
	public static String upload(String bucketName,MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			InputStream in = file.getInputStream();
			long size = file.getSize();
			String key =UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
			JAEUtils.getBucket(bucketName).object(key).entity(size,in).put();
			JAEUtils.closeJJS(jss);
			return "http://storage.jcloud.com/" + bucket + "/" + key;
		} catch (Exception e) {
			throw new RuntimeException("JAE文件添加失败");
		}
	}
	
	/**
	 * 通过MultipartFile上传
	 * @param file
	 * @return
	 */
	public static String upload(String bucketName,String newName,MultipartFile file) {
		try {
			InputStream in = file.getInputStream();
			long size = file.getSize();
			JAEUtils.getBucket(bucketName).object(newName).entity(size,in).put();
			JAEUtils.closeJJS(jss);
			return "http://storage.jcloud.com/" + bucket + "/" + newName;
		} catch (Exception e) {
			throw new RuntimeException("JAE文件添加失败");
		}
	}
	/**
	 * 上传数据指定的buckte总，返回调用路径
	 * @param bucketname
	 * @param file
	 * @param filename
	 * @return
	 */
	public static String upload(String bucketName,File file,String filename) {
		JAEUtils.getBucket(bucketName).object(filename).entity(file).put();
		JAEUtils.closeJJS(jss);
		return "http://storage.jcloud.com/" + bucketName + "/" + filename;
	}
	
	/**
	 * 通过流的方法来上传数据
	 * @param fileName
	 * @param in
	 * @param size
	 * @return
	 */
	public static String upload(String fileName,InputStream in ,long size) {
		String key =UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
		JAEUtils.getBucket().object(key).entity(size,in).put();
		JAEUtils.closeJJS(jss);
		return "http://storage.jcloud.com/" + bucket + "/" + key;
	}
	
	/**
	 * 上传到指定的bucket对象中
	 * @param bucketName
	 * @param file
	 * @return
	 */
	public static String upload(String bucketName,String fileName,InputStream in ,long size) {
		String key =UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
		JAEUtils.getBucket(bucketName).object(key).entity(size,in).put();
		JAEUtils.closeJJS(jss);
		return "http://storage.jcloud.com/" + bucketName + "/" + key;
	}

	/**
	 * 删除默认bucket中的 数据
	 * @param fileName
	 */
	public static void deleteFile(String fileName) {
		JAEUtils.getBucket().object(fileName).delete();
		JAEUtils.closeJJS(jss);
	}
	
	
	
	/**
	 * 删除指定bucket 中的所有数据
	 * @param bucketName 
	 * @param fileName
	 */
	public static void deleteFile(String bucketName,String fileName) {
		JAEUtils.getBucket(bucketName).object(fileName).delete();
		JAEUtils.closeJJS(jss);
	}

	public static void clearAllFiles(String bucketName) {
		JingdongStorageService jss = JAEUtils.getJJS();
		BucketService buck = jss.bucket(bucketName);
		// 获取所有的对象
		ObjectListing objs = buck.listObject();
		List<ObjectSummary> sumys = objs.getObjectSummaries();
		if (sumys.size() > 0) {
			// 删除所有的 文件
			for (ObjectSummary obj : sumys) {
				buck.object(obj.getKey()).delete();
			}
		}
		jss.destroy();
	}

	public static void clearAllFiles() {
		BucketService buck = JAEUtils.getBucket();
		// 获取所有的对象
		ObjectListing objs = buck.listObject();
		List<ObjectSummary> sumys = objs.getObjectSummaries();
		if (sumys.size() > 0) {
			// 删除所有的 文件
			for (ObjectSummary obj : sumys) {
				buck.object(obj.getKey()).delete();
			}
		}
		JAEUtils.closeJJS(jss);
	}
	/**
	 * 关闭连接
	 * 
	 * @param jss
	 */
	public static void closeJJS(JingdongStorageService jss) {
		jss.destroy();
	}

}

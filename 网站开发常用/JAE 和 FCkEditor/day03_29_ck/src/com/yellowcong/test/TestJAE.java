package com.yellowcong.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jcloud.jss.Credential;
import com.jcloud.jss.JingdongStorageService;
import com.jcloud.jss.domain.Bucket;
import com.jcloud.jss.domain.ObjectListing;
import com.jcloud.jss.domain.ObjectSummary;


public class TestJAE {
	private JingdongStorageService jss ;
	
	//获取京东存储服务
	@Before
	public void setUp(){
		//在使用JingdongStorageService之前，需要创建Credential对象，该对象必须包含用户的AccessKeyId以及SecertAccessKeyId
		Credential credential = new Credential("f4f006743e5f44498407f9ef81baf6a2", "e236312752a84ce19be3dc6ea3e58d61ZU8FSWCx");
		//通过Credential对象来创建JingdongStorageService
		jss = new JingdongStorageService(credential);

	}
	
	//测试创建bucket
	@Test
	public void testCreateBucket(){
		//这样直接创建的bucket对象是 私有的，不可以通过链接直接访问
		jss.createBucket("hhxy");
		//设定公共的bucket 可以通过连接访问
		jss.bucket("hhxy").acl().allowAnyoneRead().set();;
	}
	
	//测试 上传文件
	@Test
	public void testUploadFile(){
		File imge = new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg");
		String filename = imge.getName();
		//上传文件， 期货总 object("") 这个里面是文件的名称
		jss.bucket("hhxy").object("xx.img").entity(imge).put();
	}
	
	//测试文件流上传
	//不建议通过流的方法来传递文件， 如果是大的文件我们可以通过 端点上传
	@Test
	public void testUploadInputStream(){
		try {
			File imge = new File("D:\\娱乐\\图片\\桌面壁纸\\美女\\Picture 72695967.jpg");
			long length = imge.length();
			InputStream in = new FileInputStream(imge);
			//上传文件， 期货总 object("") 这个里面是文件的名称
			//通过image文件获取 文件的长度，然后通过流上传对象
			jss.bucket("hhxy").object("22.img").entity(length,in).put();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExistFile(){
		boolean flag = jss.bucket("hhxy").object("22.img").exist();
		if(flag){
			System.out.println("存在文件");
		}else{
			System.out.println("文件不存在");
		}
	}
	@Test
	public void testDownloadFiles() throws  Exception{
		//获取文件并 写到磁盘中
		jss.bucket("hhxy").object("22.img").get().toFile(new File("D:\\xx.jpg"));
		
		//通过流的方法来获取数据
//		InputStream input = jss.bucket("bucketName").object("Key").get().getInputStream();
	}
	
	@Test
	public void testDeleteFiles(){
		jss.bucket("hhxy").object("22.img").delete();
	}
	//获取里面的所有数据
	@Test
	public void testListAllFiles(){
		ObjectListing objs = jss.bucket("hhxy").listObject();
		
		for(ObjectSummary obj :objs.getObjectSummaries()){
			//获取里面的key
			System.out.println(obj.getKey());
		}
	}
	
	//删除buckets
	@Test
	public void testDeleteBuckets(){
		//当bucket 中存在文件的时候，就不可以删除
		jss.bucket("hhxy").delete();
	}
	
	//测试获取所有数据
	@Test
	public void testGetBuckets(){

		List<Bucket> buckets = jss.listBucket();
		//获取所有的key
		for(Bucket bucket:buckets) {
		   System.out.println("bucketName:"+bucket.getName());
		}
	}
	
	//最后关闭数据
	@After
	public void tearDown(){
		// JingdongStorageService对象内部维护一组HTTP连接池，在不使用该对象之前需要调用其shutdown方法关闭连接池，请注意，一旦调用shutdown方法，该对象就不能再次被使用，否则将会抛出异常。
		jss.destroy();
	}
	
	
	
}

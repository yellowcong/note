package com.yellowcong.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.yellowcong.utils.JAEUtils;

@Controller
@RequestMapping("/image")
public class ImageAction {
	
	
	@RequestMapping(value="/upload",method = RequestMethod.GET)
	public String upload(){
		return "image/upload";
	}
	/**
	 * 上传的数据名称， 在ckeditor中是   upload
	 * @param upload
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public void upload(MultipartFile upload,String  CKEditorFuncNum,HttpServletResponse resp) throws IOException{
		String name = upload.getOriginalFilename();
		String type = FilenameUtils.getExtension(name);
		
		List<String> types = Arrays.asList(new String[]{"jpg","png","jpeg","gif"});
		//获取callBack,我们可以直接通过 SpringMvc的方法来注入
		//设定编码，解决 乱码问题
		
		String callback = CKEditorFuncNum; 
		//向服务器端些数据 
		if(!types.contains(type)){
			resp.getWriter().println("<script type=\"text/javascript\">"+"window.parent.CKEDITOR.tools.callFunction(" + callback
				+ ",'','Type Error（.jpg/.gif/.jpeg/.png）');"+"</script>");
		}else{
			//上传到服务器中
			String urlPath = JAEUtils.upload(upload);
			//返回的图片地址是 http://xxx
			//向服务器端写数据
			// 相对路径用于显示图片 
			resp.getWriter().println("<script type=\"text/javascript\">"+"window.parent.CKEDITOR.tools.callFunction(" + callback  
			        + ",'" + urlPath + "','')"+"</script>");  
		}
		
	}
}

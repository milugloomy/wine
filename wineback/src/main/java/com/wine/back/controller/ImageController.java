package com.wine.back.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.core.ApplicationPart;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wine.back.service.ImageService;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;

@RestController
public class ImageController {
	
	@Autowired	
	private ImageService imageService;
	
	@RequestMapping("/imageAdd")
	public MyResEntity imageAdd(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws WineException{
		
		String type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String fileName=Util.getTimeStr()+"."+type;
		imageService.saveOriginal(file, fileName);
		imageService.saveCompressed(fileName);
		imageService.saveIcon(fileName);
		
		return new MyResEntity(fileName);
	}
	
	@RequestMapping("/imageAdd2")
	public MyResEntity imageAdd2(HttpServletRequest request) throws WineException, IOException, ServletException{
		if(FileUpload.isMultipartContent(new ServletRequestContext(request))){
			ApplicationPart part=(ApplicationPart)request.getPart("file");
			part.write("D://a.jpg");
		}
		return new MyResEntity();
	}
	
}

package com.wine.back.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wine.back.service.ImageService;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;

@Controller
public class ImageController {
	
	@Autowired	
	private ImageService imageService;
	
	@RequestMapping("/imageAdd")
	@ResponseBody
	public MyResEntity imageAdd(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws WineException{
		
		String type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String fileName=Util.getTimeStr()+"."+type;
		imageService.saveOriginal(file, fileName);
		imageService.saveCompressed(fileName);
		imageService.saveIcon(fileName);
		
		return new MyResEntity(fileName);
	}
	
	
	
}

package com.wine.back.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wine.back.service.ImageService;
import com.wine.base.bean.ImageSetting;
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
	
	@RequestMapping("/imageTest")
	public MyResEntity imageTest(HttpServletRequest request,@RequestParam("file") MultipartFile file,
			Double compressedRate,Integer compressedPix,Integer iconPix) throws WineException, IOException, ServletException{
		String type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String fileName=Util.getTimeStr()+"."+type;
		imageService.saveOriginal(file, fileName);
		imageService.saveCompressed(fileName,compressedRate,compressedPix);
		imageService.saveIcon(fileName,iconPix);
		
		return new MyResEntity(fileName);
	}
	
	@RequestMapping("/saveImageConfig")
	public MyResEntity saveImageConfig(Double compressedRate,Integer compressedPix,
			Integer iconPix){
		imageService.saveImageConfig(compressedRate,compressedPix,iconPix);
		return new MyResEntity();
	}
	
	@RequestMapping("/queryImageConfig")
	public MyResEntity queryImageConfig(Double compressedRate,Integer compressedPix,
			Integer iconPix){
		ImageSetting imageSetting=imageService.queryImageConfig();
		return new MyResEntity(imageSetting);
	}
}

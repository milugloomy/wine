package com.wine.back.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wine.base.bean.ImageSetting;
import com.wine.base.common.WineException;
import com.wine.base.dao.ImageSettingMapper;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageService {
	
	private Logger log = LoggerFactory.getLogger(ImageService.class);

	@Value("${img.path}")
    private String imgPath;
	//原图路径
	private String originalPath;
	//压缩路径
	private String compressedPath;
	//压缩成图标路径
	private String iconPath;
	
	private ImageSetting imageSetting;
	
	@Autowired
	private ImageSettingMapper imageSettingMapper;
	
	
	@PostConstruct
	public void pathInit(){
		originalPath=imgPath+"original/";
		compressedPath=imgPath+"compressed/";
		iconPath=imgPath+"icon/";
		
		imageSetting=imageSettingMapper.select();
	}
	
	//file上传
	public void saveOriginal(MultipartFile file,String fileName) throws WineException{
		String path=originalPath+fileName;
		try {
			file.transferTo(new File(path));
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage());
			throw new WineException("img.save.original.error");
		}
	}
	
	//base64的上传
	public void saveOriginal(String imgContent,String fileName) throws WineException{
		String path=originalPath+fileName;
		try {
			FileOutputStream write = new FileOutputStream(new File(path));
			byte[] decoderBytes = Base64.getDecoder().decode(imgContent);
			write.write(decoderBytes);
			write.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new WineException("img.save.original.error");
		}		
	}
	
	public void saveCompressed(String fileName) throws WineException{
		//取默认参数
		Double compressedRate=imageSetting.getCompressedRate();
		Integer compressedPix=imageSetting.getCompressedPix();
		saveCompressed(fileName, compressedRate, compressedPix);
	}
	public void saveCompressed(String fileName,Double compressedRate,Integer compressedPix) throws WineException{
		try {
			BufferedImage originalImg=ImageIO.read(new File(originalPath+fileName));
			int height=originalImg.getHeight();
			int width=originalImg.getWidth();
			//大图压缩尺寸到800内
			if(height>compressedPix || width>compressedPix){
				Thumbnails.of(originalImg)
				.size(compressedPix,compressedPix)//宽高
			    .keepAspectRatio(true)//宽高保持比例
				.outputQuality(compressedRate/100.0)//质量
//				.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(shuiyin_img)),0.5f)//水印
			    .toFile(compressedPath+fileName);
			//宽高都小于800的图不压缩尺寸
			}else{
				Thumbnails.of(originalImg)
				.scale(1)
				.outputQuality(0.5f)//质量
			    .toFile(compressedPath+fileName);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new WineException("img.save.compressed.error");
		}  
	}
	
	public void saveIcon(String fileName) throws WineException{
		//取默认参数
		Integer iconPix=imageSetting.getIconPix();
		saveIcon(fileName, iconPix);
	}
	public void saveIcon(String fileName,Integer iconPix) throws WineException{
		try {
			Thumbnails.of(originalPath+fileName)
				.size(iconPix,iconPix)//压缩后宽高
				.keepAspectRatio(true)//宽高保持比例
				.toFile(iconPath+fileName);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new WineException("img.save.icon.error");
		}
	}
	
	public void saveImageConfig(Double compressedRate, Integer compressedPix, Integer iconPix) {
		imageSetting.setCompressedPix(compressedPix);
		imageSetting.setCompressedRate(compressedRate);
		imageSetting.setIconPix(iconPix);
		imageSettingMapper.updateByPrimaryKeySelective(imageSetting);
	}
	public ImageSetting queryImageConfig() {
		return imageSetting;
	}

	public static void main(String[] args) throws IOException{
		String baseDir="D:\\Workspaces\\HkWorkspace\\mtroom\\src\\main\\resources\\public\\image\\";
		String originalDir=baseDir+"original";
		File dir=new File(originalDir);
		for(int i=0;i<dir.listFiles().length;i++){
			File file=dir.listFiles()[i];
			BufferedImage originalImg=ImageIO.read(file);
			//存压缩图
			int height=originalImg.getHeight();
			int width=originalImg.getWidth();
			//大图压缩尺寸到800内
			if(height>600 || width>600){
				Thumbnails.of(originalImg)
				.size(600,600)//宽高
			    .keepAspectRatio(true)//宽高保持比例
				.outputQuality(0.5f)//质量
			    .toFile(baseDir+"compressed\\"+file.getName());
			//宽高都小于600的图不压缩尺寸
			}else{
				Thumbnails.of(originalImg)
				.scale(1)
				.outputQuality(0.5f)//质量
			    .toFile(baseDir+"compressed\\"+file.getName());
			}
			//存图标
			Thumbnails.of(originalImg)
				.size(100,100)//压缩后宽高
				.keepAspectRatio(true)//宽高保持比例
				.toFile(baseDir+"icon\\"+file.getName());
		}
	}

}

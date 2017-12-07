package com.wine.back.batch;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wine.base.bean.Image;
import com.wine.base.common.WineException;
import com.wine.base.dao.ImageMapper;

import mybatch.common.MyBatch;

@Service
public class ImageBatch {
	private Logger log = LoggerFactory.getLogger(ImageBatch.class);
	@Autowired
	private ImageMapper imageMapper;
	@Value("${img.path}")
    private String imgPath;
	
	//每天晚上12点 删除临时图片
	@MyBatch(cron = "0 0 0 * * ?")
	public void imageClean() throws WineException{
		log.error("------删除临时图片开始------");
		List<Image> dbList=imageMapper.selectAll();
		
		File dir=new File(imgPath+"original/");
		File[] fileArr=dir.listFiles();
		
		for(int i=0;i<fileArr.length;i++){
			File originalImage=fileArr[i];
			boolean exist=false;
			for(int j=0;j<dbList.size();j++){
				if(originalImage.getName().equals(dbList.get(j).getImgUrl())){
					exist=true;
					break;
				}
			}
			if(exist==false){
				originalImage.delete();
				log.info("图片 '"+originalImage+"' 已删除");
				
				File compressedImage=new File(imgPath+"compressed/"+fileArr[i].getName());
				compressedImage.delete();
				log.info("图片 '"+compressedImage+"' 已删除");
				
				File iconImage=new File(imgPath+"icon/"+fileArr[i].getName());
				iconImage.delete();
				log.info("图片 '"+iconImage+"' 已删除");
			}
		}
		log.error("------删除临时图片结束------");
	}
	
}

package test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgCprs {
	
	public static String original_path="D:\\tmp\\prodImg\\";
	public static String conpressed_path="D:\\tmp\\prodImg\\compressed\\";
	public static String shuiyin_img="D:\\tmp\\prodImg\\compressed\\shuiyin.jpg";
	
	public static void main(String[] args) throws IOException {
		String fileName="20151224095328247.jpg";
		Thumbnails.of(original_path+fileName)  
		.size(800,800)//宽高
	    .keepAspectRatio(true)//宽高保持比例
//		.scale(1f)//size
		.outputQuality(0.5f)//质量
		.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(shuiyin_img)),0.5f)//水印
	    .toFile(conpressed_path+fileName);  
	}
}

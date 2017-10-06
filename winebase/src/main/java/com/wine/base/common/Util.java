package com.wine.base.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import sun.misc.BASE64Decoder;

public class Util {
	
	public static int pageSize=10;
	
	public static String getTimeStr() {
		Date now=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(now);
	}
	
	public static String decodeAndSave(String data,String fileName){
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			FileOutputStream write = new FileOutputStream(new File(fileName));
			byte[] decoderBytes = decoder.decodeBuffer(data);
			write.write(decoderBytes);
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}

	public static String byte2Str(byte[] byteArray){
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	public static Map<String,String> parseXml(InputStream inputStream) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			SAXReader reader = new SAXReader();
			Document document=reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			List<?> elementList = root.elements();
			// 遍历所有子节点
			for (Object o : elementList){
				Element e=(Element)o;
				map.put(e.getName(), e.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
}

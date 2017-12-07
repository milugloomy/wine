package com.wine.base.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wine.base.bean.User;

public class Util {

	public static int pageSize=10;

	public static String getTimeStr() {
		Date now=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(now);
	}
	
	public static int getUserId(HttpSession session) {
		User user=(User)session.getAttribute("user");
		return user.getUserId();
	}

	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
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
	
	public static boolean domainEquals(Object source, Object target, Class<?> clazz) {
		try {
	        Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (!method.getName().startsWith("get")) {
	                continue;
	            }
				Object srcValue = method.invoke(source, new Object[] {});
				Object tarValue = method.invoke(target, new Object[] {});
				if (!srcValue.equals(tarValue)) {
					return false;
				}
			}
			return true;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static Object getClassValue(Object obj, String fieldName) {
        Class<?> beanClass = obj.getClass();
        Method[] ms = beanClass.getMethods();
        for (int i = 0; i < ms.length; i++) {
            // 非get方法不取
            if (!ms[i].getName().startsWith("get")) {
                continue;
            }
            Object objValue = null;
            try {
                objValue = ms[i].invoke(obj, new Object[] {});
            } catch (Exception e) {
                continue;
            }
            if (objValue == null) {
                continue;
            }
            if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                    || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                return objValue;
            } else if (fieldName.toUpperCase().equals("SID")
                    && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase()
                            .equals("ID"))) {
                return objValue;
            }
        }
        return null;
    }
}

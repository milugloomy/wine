package test;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;


public class RSATest {
	public static String data="hello world";  
    public static String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/fQ+z91P1/uYJKaQ2bMtlO+wZYcVfMMsmCEgPEE3YYrkUX6XqJe7xQ2KMlrR5R45f/1wUg1mnKtGLuBHmZVkQwd1LO0aXAkUdYAWitoJW0SMIkvk1bTehggX7fUE0aKUpSCkIykOF0LOn8UEADXgIRF6dKd67t2Lwcpn4CHmsYwIDAQAB";  
    public static String privateKeyString="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL99D7P3U/X+5gkppDZsy2U77BlhxV8wyyYISA8QTdhiuRRfpeol7vFDYoyWtHlHjl//XBSDWacq0Yu4EeZlWRDB3Us7RpcCRR1gBaK2glbRIwiS+TVtN6GCBft9QTRopSlIKQjKQ4XQs6fxQQANeAhEXp0p3ru3YvBymfgIeaxjAgMBAAECgYADZqk8vQvUtq5HQ2ITRWNnbOUInl/vDOLOzh7ZhaScT0SSRBCiVAImbtf1P0f9T3QL6HEuPBb/jRcjsCVBPlOSnL5kFjVd6XvGO1RNL6t5NpskczDaJJzal4cuhybQkf8BVW0E2DDpkqYpAJiKyjcrCu69jCfPFOEPDkLuMTsHgQJBAOnxSdN1kQzaTz/VqYNJ+V8bOnUWgT1r2YJFMl5KElmq0HT0nsT0MnaSspXTcA98HFUMRQ97XVBArujVAgre0NECQQDRiw2XAIB+SI77j8AGNYgYOGXLyOYCRVu1d1D3FRrXpgpvR0TOYWlxD3DK0DoNY3PUialH1eab4u4dIFo+b5bzAkEAgxVymow71InW8hvUuf4PPx1Qqh8MF9CAth/z0yWKFrhByebvt8hlJk+YxZ8OIX8XmQghAkXLyZYU692/ITwsoQJBAJ7zroFnXhFtlRj6+J09KlnFHmMxqFj8qA7FzeNAXTZMvAmhtG4hssyS++q9fev+DzWgn4rglaiWwi3SBuLp30UCQQCvN1j7oP47Rg747T2JWSuU6MHREjqSw0N/rA1Y2aWynWVSrDG0lKZvPulmOw+xPo+K9SQAEa/Q6zhS8DCwY5Zh";  
      
    public static void main(String[] args) throws Exception {  
        //获取公钥   
        PublicKey publicKey=getPublicKey(publicKeyString);  
          
        //获取私钥   
        PrivateKey privateKey=getPrivateKey(privateKeyString);        
          
        //公钥加密  
        byte[] encryptedBytes=encrypt(data.getBytes(), publicKey);
        String p=Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("加密后："+p);  
        //私钥解密  
        byte[] decryptedBytes=decrypt(Base64.getDecoder().decode(p), privateKey);        
//        byte[] decryptedBytes=decrypt(Base64.getDecoder().decode("I5VwgEITfYCDd0w1ZaDNnF4CnW8UdMA9paW1NzuBAZIONHpIcbFRAx4GoDylED4wKkV3RmwMDayQtjHPWy6JJE75CUMKj36HvIp8L++J6Nv3Ger/vAjOSdu38u9E9hSuKgcTHyYI0L/DQ2OY3KbYX6aTsZ4Qvj823sW3QoG7quk="), privateKey);        
        System.out.println("解密后："+new String(decryptedBytes));
    }  
      
    //将base64编码后的公钥字符串转成PublicKey实例  
    public static PublicKey getPublicKey(String publicKey) throws Exception{  
        byte[ ] keyBytes=Base64.getDecoder().decode(publicKey.getBytes());  
        X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
        return keyFactory.generatePublic(keySpec);    
    }  
      
    //将base64编码后的私钥字符串转成PrivateKey实例  
    public static PrivateKey getPrivateKey(String privateKey) throws Exception{  
        byte[ ] keyBytes=Base64.getDecoder().decode(privateKey.getBytes());  
        PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
        return keyFactory.generatePrivate(keySpec);  
    }  
      
    //公钥加密  
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        return cipher.doFinal(content);  
    }  
      
    //私钥解密  
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        return cipher.doFinal(content);  
    }  
}

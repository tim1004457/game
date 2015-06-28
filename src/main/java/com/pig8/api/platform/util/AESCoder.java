package com.pig8.api.platform.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author navy
 *
 */
public class AESCoder {
	/** 
	 * 加密 
	 * @param content 需要加密的内容 
	 * @param keyWord 加密密钥
	 * @return 加密后的字符串
	 */
	public static String encrypt(String content, String keyWord) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(keyWord.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
			byte[] result = cipher.doFinal(byteContent);//加密  
			return new String(Base64.encode(result));//base64
		} catch (Exception e) {
			LogUtils.error(e);
		}
		return null;
	}

	/**
	 * 解密
	 * @param encodeStr 待解密串 
	 * @param keyWord 解密密钥 
	 * @return 已解密串
	 */
	public static String decrypt(String encodeStr, String keyWord) {
		try {
			byte[] content = Base64.decode(encodeStr);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(keyWord.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
			byte[] result = cipher.doFinal(content);
			return new String(result);
		} catch (Exception e) {
			LogUtils.error(e);
		}
		return null;
	}

	public static void main(String[] args) {

		String content = "18576671315";
		String Key = "hy@)!$";

		//加密
		System.out.println("加密前：" + content);
		String encodeStr = encrypt(content, Key);

		System.out.println("加密后：" + encodeStr);

		//解密  
		String decryptStr = decrypt(encodeStr, Key);
		System.out.println("解密后：" + decryptStr);
	}
}
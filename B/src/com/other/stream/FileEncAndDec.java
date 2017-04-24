package com.other.stream;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileEncAndDec {
	
	private static int dataOfFile = 0; // 文件字节内容
	private static String key = "ajksdfjks&^%$$^Q**";
	private static int[] array; //存放每个hash值的数组
	public static void main(String[] args) {

		File srcFile = new File("/Users/caojingkun/Desktop/今日主题.txt"); // 初始文件
		File encFile = new File("/Users/caojingkun/Desktop/encFile.txt"); // 加密文件
		File decFile = new File("/Users/caojingkun/Desktop/decFile.txt"); // 解密文件
		
		array = string2ASCII(getMD5(key));
		
		try {
			//EncFile(srcFile, encFile); //加密操作
			DecFile(encFile, decFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void EncFile(File srcFile, File encFile) throws Exception {
		if (!srcFile.exists()) {
			System.out.println("source file not exixt");
			return;
		}

		if (!encFile.exists()) {
			System.out.println("encrypt file created");
			encFile.createNewFile();
		}
		InputStream fis = new FileInputStream(srcFile);
		OutputStream fos = new FileOutputStream(encFile);

		int i = 0;
		while ((dataOfFile = fis.read()) > -1) {
			
			fos.write(dataOfFile ^ array[i++]);
			if(i==array.length-1) i=0;
		}
		System.out.println(i + "");

		fis.close();
		fos.flush();
		fos.close();
	}

	// 4176587
	private static void DecFile(File encFile, File decFile) throws Exception {
		if (!encFile.exists()) {
			System.out.println("encrypt file not exixt");
			return;
		}

		if (!decFile.exists()) {
			System.out.println("decrypt file created");
			decFile.createNewFile();
		}

		InputStream fis = new FileInputStream(encFile);
		OutputStream fos = new FileOutputStream(decFile);

		int i = 0;
		while ((dataOfFile = fis.read()) > -1) {

			fos.write(dataOfFile ^ array[i++]);
			if(i==array.length-1) i=0;
		}
		System.out.println(i + "");

		fis.close();
		fos.flush();
		fos.close();
	}

	//String2Ascii
	public static int[] string2ASCII(String s) {// 字符串转换为ASCII码
		if (s == null || "".equals(s)) {
			return null;
		}

		char[] chars = s.toCharArray();
		int[] asciiArray = new int[chars.length];

		for (int i = 0; i < chars.length; i++) {
			asciiArray[i] = char2ASCII(chars[i]);
		}
		return asciiArray;
	}
	public static int char2ASCII(char c) {
		return (int) c;
	}
	
	 public static String getMD5(String sInput) {

         String algorithm = "";
         if (sInput == null) {
             return "null";
         }
         try {
             algorithm = System.getProperty("MD5.algorithm", "MD5");
         } catch (SecurityException se) {
         }
         MessageDigest md = null;
         try {
             md = MessageDigest.getInstance(algorithm);
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         }
         byte buffer[] = sInput.getBytes();

         for (int count = 0; count < sInput.length(); count++) {
             md.update(buffer, 0, count);
         }
         byte bDigest[] = md.digest();
         BigInteger bi = new BigInteger(bDigest);
         return (bi.toString(16));
     }
}

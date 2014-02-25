package com.jikuibu.app.utils;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * åŠ å¯†è§£å¯†å·¥å…·åŒ…
 * @author Winter Lau
 * @date 2011-12-26
 */
public class CyptoUtils {

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	
    /**
     * DESç®—æ³•ï¼ŒåŠ å¯†
     *
     * @param data å¾…åŠ å¯†å­—ç¬¦ä¸²
     * @param key  åŠ å¯†ç§�é’¥ï¼Œé•¿åº¦ä¸�èƒ½å¤Ÿå°�äºŽ8ä½�
     * @return åŠ å¯†å�Žçš„å­—èŠ‚æ•°ç»„ï¼Œä¸€èˆ¬ç»“å�ˆBase64ç¼–ç �ä½¿ç”¨
     * @throws InvalidAlgorithmParameterException 
     * @throws Exception 
     */
    public static String encode(String key,String data) {
    	if(data == null)
    		return null;
    	try{
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());	    	
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        //keyçš„é•¿åº¦ä¸�èƒ½å¤Ÿå°�äºŽ8ä½�å­—èŠ‚
	        Key secretKey = keyFactory.generateSecret(dks);
	        Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
	        IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
	        AlgorithmParameterSpec paramSpec = iv;
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);           
	        byte[] bytes = cipher.doFinal(data.getBytes());            
	        return byte2hex(bytes);
    	}catch(Exception e){
    		e.printStackTrace();
    		return data;
    	}
    }

    /**
     * DESç®—æ³•ï¼Œè§£å¯†
     *
     * @param data å¾…è§£å¯†å­—ç¬¦ä¸²
     * @param key  è§£å¯†ç§�é’¥ï¼Œé•¿åº¦ä¸�èƒ½å¤Ÿå°�äºŽ8ä½�
     * @return è§£å¯†å�Žçš„å­—èŠ‚æ•°ç»„
     * @throws Exception å¼‚å¸¸
     */
    public static String decode(String key,String data) {
    	if(data == null)
    		return null;
        try {
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //keyçš„é•¿åº¦ä¸�èƒ½å¤Ÿå°�äºŽ8ä½�å­—èŠ‚
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(hex2byte(data.getBytes())));
        } catch (Exception e){
    		e.printStackTrace();
    		return data;
        }
    }

	/**
	 * äºŒè¡Œåˆ¶è½¬å­—ç¬¦ä¸²
	 * @param b
	 * @return
	 */
    private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b!=null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
    
    private static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0)
            throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length/2];
		for (int n = 0; n < b.length; n+=2) {
		    String item = new String(b,n,2);
		    b2[n/2] = (byte)Integer.parseInt(item,16);
		}
        return b2;
    }
    
}
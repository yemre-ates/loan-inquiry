package com.yea.loaninquiry.tool;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtils {
	
	private static final String encyrptMessageKey = "SecurityIsImportant";
		
    private  final static String DEFAULT_ENCODING = "UTF-8"; 
    @SuppressWarnings("restriction")
	static BASE64Encoder enc = new BASE64Encoder();
    @SuppressWarnings("restriction")
	static BASE64Decoder dec = new BASE64Decoder();

    private static String base64encode(String text) {
        try {
            return enc.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static String base64decode(String text) {
        try {
            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }
    
    public static String  encode(String message) {
    	String text = xorMessage(message, encyrptMessageKey);
    	return base64encode(text);
    }
    
    public static String  decode(String message) {
    	String text = base64decode(message);
    	return xorMessage(text, encyrptMessageKey);
    }

    private static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char)(mesg[i] ^ keys[i % kl]);
            }

            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }
}
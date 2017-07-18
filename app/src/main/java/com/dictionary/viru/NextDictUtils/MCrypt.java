package com.dictionary.viru.NextDictUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MCrypt {
//	public String IV = "f7h9a5d0gkvi7v6v";
	public String IV = (new Object() {int t;public String toString() {byte[] buf = new byte[16];t = 354104874;buf[0] = (byte) (t >>> 11);t = 889054958;buf[1] = (byte) (t >>> 5);t = 1698072580;buf[2] = (byte) (t >>> 12);t = -729166357;buf[3] = (byte) (t >>> 11);t = 253357763;buf[4] = (byte) (t >>> 1);t = 894887451;buf[5] = (byte) (t >>> 24);t = -350185121;buf[6] = (byte) (t >>> 19);t = -1290241864;buf[7] = (byte) (t >>> 15);t = 586259683;buf[8] = (byte) (t >>> 10);t = 40622745;buf[9] = (byte) (t >>> 16);t = -825188378;buf[10] = (byte) (t >>> 21);t = 1658035468;buf[11] = (byte) (t >>> 17);t = -667483748;buf[12] = (byte) (t >>> 16);t = 994818025;buf[13] = (byte) (t >>> 23);t = 1742957573;buf[14] = (byte) (t >>> 12);t = -1598433053;buf[15] = (byte) (t >>> 10);return new String(buf);}}.toString());
//	public String IKEY = "d7f8g9hk0dj2s712";
	public String IKEY = (new Object() {int t;public String toString() {byte[] buf = new byte[16];t = -1048370475;buf[0] = (byte) (t >>> 11);t = 1908865917;buf[1] = (byte) (t >>> 13);t = -1764128005;buf[2] = (byte) (t >>> 14);t = 1826210241;buf[3] = (byte) (t >>> 3);t = -862917825;buf[4] = (byte) (t >>> 3);t = 1191825371;buf[5] = (byte) (t >>> 11);t = -1969535759;buf[6] = (byte) (t >>> 11);t = -1611516535;buf[7] = (byte) (t >>> 7);t = 2089550714;buf[8] = (byte) (t >>> 14);t = -916774051;buf[9] = (byte) (t >>> 11);t = -86679811;buf[10] = (byte) (t >>> 17);t = 830333335;buf[11] = (byte) (t >>> 3);t = 1750568559;buf[12] = (byte) (t >>> 5);t = -701575694;buf[13] = (byte) (t >>> 6);t = -821678071;buf[14] = (byte) (t >>> 13);t = -2033411692;buf[15] = (byte) (t >>> 14);return new String(buf);}}.toString());
	private IvParameterSpec ivParam;
	private SecretKeySpec secretKey;
	private Cipher cipher;
	
	
	public MCrypt(String iv, String key) {
		ivParam = new IvParameterSpec(iv.getBytes());
		secretKey = new SecretKeySpec(key.getBytes(), "AES");
		
		try {
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParam);
		} catch (Exception e) {
			e.printStackTrace();
			cipher = null;
		}
	}
	
	public String decrypt(String encryptedData ) {
		if (cipher != null) {
			try {
				byte[] res = cipher.doFinal(byteStrToByteArr(encryptedData));
				return new String(res).trim();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		    
		return null;
	}
	
	private byte[] byteStrToByteArr(String str) {
		if (str == null) return null;
		int length = str.length()/2;
		byte[] res = new byte[length];
		for (int k = 0; k < length; k++)
			res[k] = ((byte) Integer.parseInt(str.substring(2 * k, 2 * k + 2), 16));
		return res;
	}
	
	public static String generateSecretKey(String secret, String str) {
		byte[] md5 = Md5.md5(secret + str);
		if (md5 == null || md5.length <=0) {
			return null;
		}
		int odd = (md5[0] & 0xff) % 2;
		
		byte[] res = new byte[md5.length/2];
		
		for (int i = 0; i < md5.length/2; i ++) {
			res[i] = md5[i * 2 + odd];
		}
		
		return Md5.bin2Hex(res);
	}
}

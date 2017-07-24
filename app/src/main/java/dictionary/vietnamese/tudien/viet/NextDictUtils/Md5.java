package dictionary.vietnamese.tudien.viet.NextDictUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static byte[] md5(String in) {
	    try {
	    	MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.reset();
	        digest.update(in.getBytes());
	        return digest.digest();
	        
	    } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
	    return null;
	}
	
	public static String bin2Hex(byte[] a) {
		if (a == null || a.length <= 0) {
			return null;
		}
		int len = a.length;
        StringBuilder sb = new StringBuilder(len << 1);
        for (int i = 0; i < len; i++) {
            sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(a[i] & 0x0f, 16));
        }
        return sb.toString();
	}

	public static String md5S(String in) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			return bin2Hex(digest.digest());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}

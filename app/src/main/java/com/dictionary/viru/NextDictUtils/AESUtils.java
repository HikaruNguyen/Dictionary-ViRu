package com.dictionary.viru.NextDictUtils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by manhi on 14/3/2016.
 */
public class AESUtils {
    public static final String masterKey = (new Object() {
        int t;

        public String toString() {
            byte[] buf = new byte[14];
            t = 510872823;
            buf[0] = (byte) (t >>> 16);
            t = 312649772;
            buf[1] = (byte) (t >>> 9);
            t = 1568396859;
            buf[2] = (byte) (t >>> 22);
            t = 71666062;
            buf[3] = (byte) (t >>> 20);
            t = -1893546449;
            buf[4] = (byte) (t >>> 21);
            t = -1999075664;
            buf[5] = (byte) (t >>> 17);
            t = 1106992688;
            buf[6] = (byte) (t >>> 24);
            t = -420830051;
            buf[7] = (byte) (t >>> 20);
            t = 816203100;
            buf[8] = (byte) (t >>> 12);
            t = -562216713;
            buf[9] = (byte) (t >>> 8);
            t = 1225050506;
            buf[10] = (byte) (t >>> 10);
            t = -1911019772;
            buf[11] = (byte) (t >>> 15);
            t = -1728279687;
            buf[12] = (byte) (t >>> 23);
            t = -618983667;
            buf[13] = (byte) (t >>> 15);
            return new String(buf);
        }
    }.toString());
    public byte[] key;
    public byte[] iv;

    public void setKey(String encryptKey) {
        String md5 = Md5.bin2Hex(Md5.md5(encryptKey + masterKey));

        String keyString = "";
        String ivString = "";

        for (int i = 0; i < 32; i++) {
            if (i % 2 == 0) {
                keyString += md5.charAt(i);
            } else {
                ivString += md5.charAt(i);
            }
        }

        key = keyString.getBytes();
        iv = ivString.getBytes();
    }

    public String decrypt(String text) throws Exception {
        if (key == null || iv == null) {
            throw new NullPointerException("MCrypt: encryption key not initialized. Please use setKey() first!");
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] results = cipher.doFinal(decoder.decodeBuffer(text));
        byte[] results = cipher.doFinal(Base64.decode(text, Base64.DEFAULT));
        return new String(results, "UTF-8");
    }

    public String decrypt2(byte[] data) throws Exception {
        if (key == null || iv == null) {
            throw new NullPointerException("MCrypt: encryption key not initialized. Please use setKey() first!");
        }

        data = decompress(decryptCommon(data));

        return new String(data, "UTF-8");
    }

    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    public byte[] decryptCommon(byte[] data) throws Exception {
        for (int i = 0; i < data.length; i++) {
            int j = i / 2;
            int one = (int) data[i];
            int two;

            if (i % 2 == 1) {
                two = (int) key[j % 16];
            } else {
                two = (int) iv[j % 16];
            }

            int xor = one ^ two;

            byte b = (byte) (0xff & xor);

            data[i] = b;
        }

        return data;
    }

}

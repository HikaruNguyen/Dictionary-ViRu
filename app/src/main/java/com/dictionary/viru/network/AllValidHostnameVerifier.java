package com.dictionary.viru.network;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AllValidHostnameVerifier implements HostnameVerifier {
    public boolean verify(String paramString, SSLSession paramSSLSession) {
        return true;
    }
}

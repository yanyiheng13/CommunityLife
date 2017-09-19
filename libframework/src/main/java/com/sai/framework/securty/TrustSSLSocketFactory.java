package com.sai.framework.securty;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author: huajie
 * @version: 1.0
 * @date: 2016/12/29
 * @email: huajie@le.com
 * @Copyright (c) 2016. le.com Inc. All rights reserved.
 */
public class TrustSSLSocketFactory {

    public static SSLSocketFactory initSSL(Context context, int rawId) {
        return initSSL(context.getResources().openRawResource(rawId));
    }

    public static SSLSocketFactory initSSL(InputStream... ins) {
        if (ins == null) {
            return null;
        }
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream is = null;
            keystore.load(null, null);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            for (int index = 0; index < ins.length; index++) {
                is = ins[index];
                keystore.setCertificateEntry(String.valueOf(index), certificateFactory.generateCertificate(is));
                if (is != null) {
                    is.close();
                }
            }

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keystore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

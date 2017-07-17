package com.jiang.common.utils.glide;


import android.util.Log;

import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by jiang on 2017/5/24.
 */

public class OkhttpClientHelper {

    private static final String TAG = OkHttpGlideModule.class.getName();
    /**
     * 忽略所有https证书
     */
    public static OkHttpClient.Builder overlockCard(OkHttpClient.Builder client) {
        SSLContext sslContext;

        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                            X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        X509Certificate[] x509Certificates = new X509Certificate[0];
                        return x509Certificates;
                    }
                }};
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (Exception e) {
            Log.e(TAG, "ssl出现异常");
            return client;
        }
        client.sslSocketFactory(sslContext.getSocketFactory());
        return client;
    }
}

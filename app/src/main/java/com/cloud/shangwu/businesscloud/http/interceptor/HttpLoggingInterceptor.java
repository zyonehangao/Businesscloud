package com.cloud.shangwu.businesscloud.http.interceptor;

import android.util.Log;

import com.cloud.shangwu.businesscloud.constant.Constant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by chengxiaofen on 2018/8/20.
 */

public class HttpLoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");
    private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = null;
        if (requestBody != null) {
            okio.Buffer buffer = new okio.Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                if (request.url().toString().contains(Constant.BASE_URL)) {
                    charset = contentType.charset(UTF8);
                }else {
                    MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

                }
            }
            body = buffer.readString(charset);
            body.replaceAll("\\\\","");
        }

        Log.i("商务云请求",
                "发送请求: method：" + request.method()
                        + "\nurl：" + request.url()
                        + "\n请求头：" + request.headers() +
                        "\n是不是https：" + request.isHttps() + request.cacheControl()
                        + "\n请求参数: " + body);
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        String rBody = null;
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        okio.Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {

                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);

        Log.i("商务云结果",
             "收到响应: code:" + response.code()
                        + "\n请求url：" + response.request().url()
                        + "\n请求头：" + chain.request().headers()
                        + "\n请求body：" + body
                        + "\n编码格式：" + charset
                        + "\n " + rBody);
        return response;
    }
}

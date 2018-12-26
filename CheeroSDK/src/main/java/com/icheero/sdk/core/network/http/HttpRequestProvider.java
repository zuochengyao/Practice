package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.origin.OriginHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.volley.VolleyRequestFactory;
import com.icheero.sdk.util.Common;

public class HttpRequestProvider
{
    private static final String CLASSNAME_OKHTTP = "okhttp3.OkHttpClient";
    private static final String CLASSNAME_VOLLEY = "com.android.volley.toolbox.Volley";

    private IHttpRequestFactory mHttpRequestFactory;

    private static volatile HttpRequestProvider mInstance;

    private HttpRequestProvider()
    {
        // 是否支持okhttp3
        if (Common.isClassExist(CLASSNAME_OKHTTP, HttpRequestProvider.class.getClassLoader()))
            mHttpRequestFactory = new OkHttpRequestFactory();
        // 是否支持volley
        else if (Common.isClassExist(CLASSNAME_VOLLEY, HttpRequestProvider.class.getClassLoader()))
            mHttpRequestFactory = new VolleyRequestFactory();
        // 若都不支持，则使用android自带的
        else
            mHttpRequestFactory = new OriginHttpRequestFactory();
    }

    public static HttpRequestProvider getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpRequestProvider.class)
            {
                if (mInstance == null)
                    mInstance = new HttpRequestProvider();
            }
        }
        return mInstance;
    }

    IHttpCall getHttpCall(HttpRequest request)
    {
        // URI.create(request.getUrl()), request.getMethod(), request.getMediaType()
        return mHttpRequestFactory.getHttpCall(request);
    }

    IHttpRequestFactory getHttpRequestFactory()
    {
        return mHttpRequestFactory;
    }
}

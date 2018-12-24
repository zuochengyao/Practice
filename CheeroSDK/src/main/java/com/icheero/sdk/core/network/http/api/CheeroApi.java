package com.icheero.sdk.core.network.http.api;

import com.icheero.sdk.core.network.http.HttpRequestEngine;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IConvert;
import com.icheero.sdk.core.network.listener.IResponseListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheeroApi
{
    private static final String ENCODE_UTF8 = "utf-8";
    /**
     * "application/x-www-form-urlencoded"，是默认的MIME内容编码类型，一般可以用于所有的情况，但是在传输比较大的二进制或者文本数据时效率低。
     * 这时候应该使用"multipart/form-data"。如上传文件或者二进制数据和非ASCII数据。
     */
    public static final String MEDIA_TYPE_NORMAL = "application/x-www-form-urlencoded;charset=utf-8";
    /**
     * 既可以提交普通键值对，也可以提交(多个)文件键值对。
     */
    public static final String MEDIA_TYPE_MULTIPART = "multipart/form-data;charset=utf-8";
    /**
     *只能提交二进制，而且只能提交一个二进制，如果提交文件的话，只能提交一个文件,后台接收参数只能有一个，而且只能是流（或者字节数组）
     */
    public static final String MEDIA_TYPE_STREAM = "application/octet-stream";
    public static final String MEDIA_TYPE_TEXT = "text/plain;charset=utf-8";
    public static final String MEDIA_TYPE_JSON = "application/json;charset=utf-8";

    private static final List<IConvert> mConvertList = new ArrayList<>();

    public static void helloWorld(String url, Map<String, String> value, IResponseListener<String> response)
    {
        CheeroRequest request = new CheeroRequest();
        request.setUrl(url);
        request.setMethod(HttpMethod.POST);
        request.setData(encodeParam(value));
        request.setResponse(response);
        request.setMediaType(MEDIA_TYPE_NORMAL);
        HttpRequestEngine.getInstance().add(request);
    }

    private static byte[] encodeParam(Map<String, String> value)
    {
        if (value != null && value.size() > 0)
        {
            StringBuilder sb = new StringBuilder();
            int index = 0;
            for (Map.Entry<String, String> entry : value.entrySet())
            {
                try
                {
                    sb.append(URLEncoder.encode(entry.getKey(), ENCODE_UTF8)).append("=").append(URLEncoder.encode(entry.getValue(), ENCODE_UTF8));
                    if (index != value.size() - 1) sb.append("&");
                    index++;
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
            return sb.toString().getBytes();
        }
        return null;
    }
}
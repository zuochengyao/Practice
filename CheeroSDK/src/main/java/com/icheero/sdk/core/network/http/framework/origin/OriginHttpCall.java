package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.AbstractHttpCall;
import com.icheero.sdk.core.network.http.implement.HttpHeader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class OriginHttpCall extends AbstractHttpCall
{
    private HttpURLConnection mConnection;
    private String mUrl;
    private HttpMethod mMethod;
    private HttpHeader mHeader;
    private AbstractHttpEntity mData;
    private HttpResponse mListener;

    OriginHttpCall(HttpURLConnection connection, String url, HttpMethod method, HttpHeader header, AbstractHttpEntity data, HttpResponse listener)
    {
        this.mConnection = connection;
        this.mUrl = url;
        this.mMethod = method;
        this.mHeader = header;
        this.mData = data;
        this.mListener = listener;
    }

    public HttpResponse getListener()
    {
        return mListener;
    }

    @Override
    public IHttpResponse execute() throws IOException
    {
        checkExecuted();
        if (mData != null)
            getBody().write(mData.getBytes());
        for (Map.Entry<String, String> entry : mHeader.entrySet())
            mConnection.setRequestProperty(entry.getKey(), entry.getValue());
        mConnection.setUseCaches(false);
        mConnection.setDoOutput(true);
        mConnection.setDoInput(true);
        mConnection.setRequestMethod(mMethod.name());
        mConnection.connect();
        if (getBodyData() != null && getBodyData().length > 0)
        {
            OutputStream out = new DataOutputStream(mConnection.getOutputStream());
            out.write(getBodyData());
            out.close();
        }
        return new OriginHttpResponse(mConnection);
    }

    @Override
    public void enqueue()
    {
        OriginHttpManager.getInstance().enqueue(this);
    }

    @Override
    public HttpMethod getMethod()
    {
        return mMethod;
    }

    @Override
    public URI getUri()
    {
        return URI.create(mUrl);
    }

    @Override
    protected HttpHeader getHeaders()
    {
        return mHeader;
    }
}
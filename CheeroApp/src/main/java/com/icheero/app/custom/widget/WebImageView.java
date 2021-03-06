package com.icheero.app.custom.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;

import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpManager;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


/**
 * Created by zuochengyao on 2018/3/1.
 */

public class WebImageView extends AppCompatImageView
{
    private Drawable mPlaceholder, mImage;

    public WebImageView(Context context)
    {
        super(context);
    }

    public WebImageView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WebImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void setPlaceholder(Drawable drawable)
    {
        mPlaceholder = drawable;
        if (mImage == null)
            setImageDrawable(mPlaceholder);
    }

    public void setPlaceholder(int resId)
    {
        setPlaceholder(getResources().getDrawable(resId));
    }

    public void setImageUrl(String url)
    {
        DownloadTask task = new DownloadTask();
        task.execute(url);
    }

    private class DownloadTask extends AsyncTask<String, Void, Bitmap>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings)
        {
            byte[] data = new byte[0];
            try
            {
                data = Objects.requireNonNull(OkHttpManager.getInstance().syncDownload(strings[0]).body()).bytes();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            mImage = new BitmapDrawable(getContext().getResources(), bitmap);
            setImageDrawable(mImage);
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
    }
}

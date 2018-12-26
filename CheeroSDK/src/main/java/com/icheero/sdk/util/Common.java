package com.icheero.sdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import com.icheero.sdk.base.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by 左程耀 on 2018/2/26.
 */

@SuppressWarnings("unused")
public class Common
{
    public static int dp2px(Context context, float dp)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void toast(Context context, CharSequence str, int duration)
    {
        Toast.makeText(context, str, duration).show();
    }

    public static void toast(Context context, @StringRes int resID, int duration)
    {
        Toast.makeText(context, resID, duration).show();
    }

    public static String getVersionCode()
    {
        PackageManager packageManager = BaseApplication.getAppInstance().getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "1";
        try
        {
            packageInfo = packageManager.getPackageInfo(BaseApplication.getAppInstance().getPackageName(), 0);
            versionCode = packageInfo.getLongVersionCode() + "";
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName()
    {
        PackageManager packageManager = BaseApplication.getAppInstance().getPackageManager();
        PackageInfo packageInfo;
        String versionName = "1.0.0";
        try
        {
            packageInfo = packageManager.getPackageInfo(BaseApplication.getAppInstance().getPackageName(), 0);
            versionName = packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 生成一个MD5加密串
     * @param str 待加密串
     */
    public static String md5(String str)
    {
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(str)) return null;
        try
        {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(str.getBytes());
            byte[] cipher = digest.digest();
            for (byte b : cipher)
            {
                String hexStr = Integer.toHexString(b & 0xff);
                builder.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static byte[] encodeParam(Map<String, String> value, String encoding)
    {
        if (value != null && value.size() > 0)
        {
            StringBuilder sb = new StringBuilder();
            int index = 0;
            for (Map.Entry<String, String> entry : value.entrySet())
            {
                try
                {
                    sb.append(URLEncoder.encode(entry.getKey(), encoding)).append("=").append(URLEncoder.encode(entry.getValue(), encoding));
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

    public static boolean isClassExist(String className, ClassLoader loader)
    {
        boolean flag = true;
        try
        {
            Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            flag = false;
        }
        return flag;
    }

    public static Type getGenericInterfaceType(Class interfaceObj)
    {
        Type[] types = interfaceObj.getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        return parameterized.getActualTypeArguments()[0];
    }

    public static Type getGenericClassType(Class classObj)
    {
        Type type = classObj.getGenericSuperclass();
        return type != null ? ((ParameterizedType) type).getActualTypeArguments()[0] : null;
    }
}

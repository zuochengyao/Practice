package com.icheero.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager
{
    private boolean isPageEnabled = true;

    public NoScrollViewPager(@NonNull Context context)
    {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return isPageEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return isPageEnabled && super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置Pager是否可滑动
     */
    public void setPageEnabled(boolean enabled)
    {
        this.isPageEnabled = enabled;
    }
}

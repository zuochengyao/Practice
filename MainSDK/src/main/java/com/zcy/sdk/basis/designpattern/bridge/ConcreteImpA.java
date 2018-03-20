package com.zcy.sdk.basis.designpattern.bridge;

import com.zcy.sdk.util.Log;

/**
 * Created by zuochengyao on 2018/3/20.
 */

public class ConcreteImpA implements Implementor
{
    @Override
    public void operation()
    {
        Log.i(ConcreteImpA.class, "Operation A");
    }
}

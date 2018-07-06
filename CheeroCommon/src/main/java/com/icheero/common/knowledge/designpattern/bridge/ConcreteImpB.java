package com.icheero.common.knowledge.designpattern.bridge;

import com.icheero.common.util.Log;

/**
 * Created by zuochengyao on 2018/3/20.
 */

public class ConcreteImpB implements Implementor
{
    @Override
    public void operation()
    {
        Log.i(ConcreteImpB.class, "Operation B");
    }
}
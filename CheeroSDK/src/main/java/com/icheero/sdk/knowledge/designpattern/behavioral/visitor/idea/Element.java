package com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea;

/**
 * 定义一个Accept操作，它以一个访问者为参数
 * Created by zuochengyao on 2018/4/4.
 */

public abstract class Element
{
    public abstract void accept(Visitor visitor);
}

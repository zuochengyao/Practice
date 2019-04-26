package com.icheero.sdk.core.reverse.dex.model;

import java.util.ArrayList;
import java.util.List;

public class Dex
{
    private DexHeader header;
    public List<StringIdItem> stringIds;
    public List<TypeIdItem> typeIds;
    public List<ProtoIdItem> protoIds;

    public Dex(DexHeader header)
    {
        this.header = header;
        stringIds = new ArrayList<>();
        typeIds = new ArrayList<>();
        protoIds = new ArrayList<>();
    }

    public DexHeader getHeader()
    {
        return header;
    }
}
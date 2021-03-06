package com.icheero.sdk.core.reverse.dex;

import com.icheero.sdk.core.reverse.dex.model.ClassDataItem;
import com.icheero.sdk.core.reverse.dex.model.ClassDefItem;
import com.icheero.sdk.core.reverse.dex.model.Dex;
import com.icheero.sdk.core.reverse.dex.model.DexHeader;
import com.icheero.sdk.core.reverse.dex.model.EncodedField;
import com.icheero.sdk.core.reverse.dex.model.EncodedMethod;
import com.icheero.sdk.core.reverse.dex.model.FieldIdItem;
import com.icheero.sdk.core.reverse.dex.model.MethodIdItem;
import com.icheero.sdk.core.reverse.dex.model.ProtoIdItem;
import com.icheero.sdk.core.reverse.dex.model.StringIdItem;
import com.icheero.sdk.core.reverse.dex.model.TypeIdItem;
import com.icheero.sdk.core.reverse.dex.model.TypeItem;
import com.icheero.sdk.core.reverse.dex.model.Uleb128;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.IOUtils;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DexParser
{
    private static final Class TAG = DexParser.class;

    private byte[] mDexData;
    private DexHeader mDexHeader;
    private Dex mDex;
    private List<String> mStringList;
    private List<String> mTypeList;

    private static volatile DexParser mInstance;

    private DexParser()
    {
        mDexHeader = new DexHeader();
        mDex = new Dex(mDexHeader);
        mStringList = new ArrayList<>();
        mTypeList = new ArrayList<>();
    }

    public static DexParser getInstance()
    {
        if (mInstance == null)
        {
            synchronized (DexParser.class)
            {
                if (mInstance == null)
                    mInstance = new DexParser();
            }
        }
        return mInstance;
    }

    public void parse(byte[] dexData)
    {
        Log.e(TAG, "Parse dex start!");
        this.mDexData = dexData;
        parseDexHeader();
        parseDexStringIdList();
        parseTypeIdList();
        parseProtoIdList();
        parseFieldIdList();
        parseMethodIdList();
        parseClassDefItemList();
        Log.e(TAG, "Parse dex finish!");
    }

    private void parseDexHeader()
    {
        IOUtils.copyBytes(mDexData, 0, mDexHeader.magic);
        IOUtils.copyBytes(mDexData, 8, mDexHeader.checkSum);
        IOUtils.copyBytes(mDexData, 12, mDexHeader.signature);
        IOUtils.copyBytes(mDexData, 32, mDexHeader.fileSize);
        IOUtils.copyBytes(mDexData, 36, mDexHeader.headerSize);
        IOUtils.copyBytes(mDexData, 40, mDexHeader.endianTag);
        IOUtils.copyBytes(mDexData, 44, mDexHeader.linkSize);
        IOUtils.copyBytes(mDexData, 48, mDexHeader.linkOff);
        IOUtils.copyBytes(mDexData, 52, mDexHeader.mapOff);
        IOUtils.copyBytes(mDexData, 56, mDexHeader.stringIdsSize);
        IOUtils.copyBytes(mDexData, 60, mDexHeader.stringIdsOff);
        IOUtils.copyBytes(mDexData, 64, mDexHeader.typeIdsSize);
        IOUtils.copyBytes(mDexData, 68, mDexHeader.typeIdsOff);
        IOUtils.copyBytes(mDexData, 72, mDexHeader.protoIdsSize);
        IOUtils.copyBytes(mDexData, 76, mDexHeader.protoIdsOff);
        IOUtils.copyBytes(mDexData, 80, mDexHeader.fieldIdsSize);
        IOUtils.copyBytes(mDexData, 84, mDexHeader.fieldIdsOff);
        IOUtils.copyBytes(mDexData, 88, mDexHeader.methodIdsSize);
        IOUtils.copyBytes(mDexData, 92, mDexHeader.methodIdsOff);
        IOUtils.copyBytes(mDexData, 96, mDexHeader.classDefsSize);
        IOUtils.copyBytes(mDexData, 100, mDexHeader.classDefsOff);
        IOUtils.copyBytes(mDexData, 104, mDexHeader.dataSize);
        IOUtils.copyBytes(mDexData, 108, mDexHeader.dataOff);
        Log.i(TAG, mDexHeader.toString().split("\n"));
    }

    private void parseDexStringIdList()
    {
        if (mDexHeader.getStringIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexStringIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getStringIdsSizeValue(); i++)
            {
                StringIdItem stringId = new StringIdItem();
                IOUtils.copyBytes(mDexData, mDexHeader.getStringIdsOffValue() + i * 4, stringId.stringDataOff);
                stringId.stringData.val = Uleb128.from(IOUtils.copyBytes(mDexData, stringId.getStringDataOffValue()));
                stringId.stringData.data = IOUtils.copyBytes(mDexData, stringId.getStringDataOffValue() + stringId.stringData.getLength(), stringId.stringData.getValue());
                Log.i(TAG, stringId.toString());
                mDex.stringIds.add(stringId);
                mStringList.add(stringId.stringData.getDataStr());
            }
        }
    }

    private void parseTypeIdList()
    {
        if (mDexHeader.getTypeIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexTypeIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getTypeIdsSizeValue(); i++)
            {
                TypeIdItem typeId = new TypeIdItem();
                IOUtils.copyBytes(mDexData, mDexHeader.getTypeIdsOffValue() + i * 4, typeId.descriptorIdx);
                String type = mDex.stringIds.get(typeId.getDescriptorIdx()).stringData.getDataStr();
                Log.i(TAG, typeId.toString().concat(", Type: " + Common.signature2JavaType(type)));
                mDex.typeIds.add(typeId);
                mTypeList.add(type);
            }
        }
    }

    private void parseProtoIdList()
    {
        if (mDexHeader.getProtoIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexProtoIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getProtoIdsSizeValue(); i++)
            {
                ProtoIdItem protoId = new ProtoIdItem();
                IOUtils.copyBytes(mDexData, mDexHeader.getProtoIdsOffValue() + i * 12, protoId.shortyIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getProtoIdsOffValue() + i * 12 + 4, protoId.returnTypeIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getProtoIdsOffValue() + i * 12 + 8, protoId.parametersOff);
                if (protoId.getParametersOffValue() > 0)
                {
                    int size = IOUtils.byte2Int(IOUtils.copyBytes(mDexData, protoId.getParametersOffValue(), 4));
                    for (int j = 0; j < size; j++)
                    {
                        TypeItem type = new TypeItem();
                        IOUtils.copyBytes(mDexData, protoId.getParametersOffValue() + 4 + j * 2, type.typeIdx);
                        protoId.parameters.size = size;
                        protoId.parameters.list.add(type);
                    }
                }
                Log.i(TAG, protoId.toString());
                mDex.protoIds.add(protoId);
            }
        }
    }

    private void parseFieldIdList()
    {
        if (mDexHeader.getFieldIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexFieldIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getFieldIdsSizeValue(); i++)
            {
                FieldIdItem fieldId = new FieldIdItem();
                IOUtils.copyBytes(mDexData, mDexHeader.getFieldIdsOffValue() + i * 8, fieldId.classIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getFieldIdsOffValue() + i * 8 + 2, fieldId.typeIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getFieldIdsOffValue() + i * 8 + 4, fieldId.nameIdx);
                Log.i(TAG, fieldId.toString());
                mDex.fieldIds.add(fieldId);
            }
        }
    }

    private void parseMethodIdList()
    {
        if (mDexHeader.getMethodIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexMethodIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getMethodIdsSizeValue(); i++)
            {
                MethodIdItem methodId = new MethodIdItem();
                IOUtils.copyBytes(mDexData, mDexHeader.getMethodIdsOffValue() + i * 8, methodId.classIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getMethodIdsOffValue() + i * 8 + 2, methodId.protoIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getMethodIdsOffValue() + i * 8 + 4, methodId.nameIdx);
                Log.i(TAG, methodId.toString());
                mDex.methodIds.add(methodId);
            }
        }
    }

    private void parseClassDefItemList()
    {
        if (mDexHeader.getClassDefsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ ClassDefItemList ------------------\n");
            for (int i = 0; i < mDexHeader.getClassDefsSizeValue(); i++)
            {
                ClassDefItem classDef = new ClassDefItem();
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32, classDef.classIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32 + 4, classDef.accessFlags);
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32 + 8, classDef.superclassIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32 + 12, classDef.interfacesOff);
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32 + 16, classDef.sourceFileIdx);
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32 + 20, classDef.annotationsOff);
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32 + 24, classDef.classDataOff);
                IOUtils.copyBytes(mDexData, mDexHeader.getClassDefsOffValue() + i * 32 + 28, classDef.staticValueOff);
                Log.i(TAG, classDef.toString());
                if (classDef.getClassDataOffValue() > 0)
                {
                    ClassDataItem classData = parseClassDataItem(IOUtils.copyBytes(mDexData, classDef.getClassDataOffValue()));
                    Log.i(TAG, classData.toString());
                }
                if (classDef.getStaticValueOffValue() > 0)
                {
                    // TODO
                }
                mDex.classDefs.add(classDef);
            }
        }
    }

    private ClassDataItem parseClassDataItem(byte[] src)
    {
        ClassDataItem classData = null;
        int offset = 0;
        if (src.length > 0)
        {
            classData = new ClassDataItem();
            classData.staticFieldsSize = Uleb128.from(src);
            offset += classData.staticFieldsSize.getLength();
            classData.instanceFieldsSize = Uleb128.from(IOUtils.copyBytes(src, offset));
            offset += classData.instanceFieldsSize.getLength();
            classData.directMethodsSize = Uleb128.from(IOUtils.copyBytes(src, offset));
            offset += classData.directMethodsSize.getLength();
            classData.virtualMethodsSize = Uleb128.from(IOUtils.copyBytes(src, offset));
            offset += classData.virtualMethodsSize.getLength();

            classData.staticFields = new EncodedField[(int) classData.staticFieldsSize.asLong()];
            for (int i = 0; i < classData.staticFields.length; i++)
            {
                EncodedField staticField = new EncodedField();
                staticField.filedIdxDiff = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += staticField.filedIdxDiff.getLength();
                staticField.accessFlags = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += staticField.accessFlags.getLength();
                classData.staticFields[i] = staticField;
            }

            classData.instanceFields = new EncodedField[(int) classData.instanceFieldsSize.asLong()];
            for (int i = 0; i < classData.instanceFields.length; i++)
            {
                EncodedField staticField = new EncodedField();
                staticField.filedIdxDiff = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += staticField.filedIdxDiff.getLength();
                staticField.accessFlags = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += staticField.accessFlags.getLength();
                classData.instanceFields[i] = staticField;
            }

            classData.directMethods = new EncodedMethod[(int) classData.directMethodsSize.asLong()];
            for (int i = 0; i < classData.directMethods.length; i++)
            {
                EncodedMethod directMethod = new EncodedMethod();
                directMethod.methodIdxDiff = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += directMethod.methodIdxDiff.getLength();
                directMethod.accessFlags = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += directMethod.accessFlags.getLength();
                directMethod.codeOff = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += directMethod.codeOff.getLength();
                classData.directMethods[i] = directMethod;
            }

            classData.virtualMethods = new EncodedMethod[(int) classData.virtualMethodsSize.asLong()];
            for (int i = 0; i < classData.virtualMethods.length; i++)
            {
                EncodedMethod directMethod = new EncodedMethod();
                directMethod.methodIdxDiff = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += directMethod.methodIdxDiff.getLength();
                directMethod.accessFlags = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += directMethod.accessFlags.getLength();
                directMethod.codeOff = Uleb128.from(IOUtils.copyBytes(src, offset));
                offset += directMethod.codeOff.getLength();
                classData.virtualMethods[i] = directMethod;
            }
        }
        return classData;
    }

    public String getDataString(int index)
    {
        return mStringList.get(index);
    }

    public String getTypeString(int index)
    {
        return mTypeList.get(index);
    }

    public ProtoIdItem getProtoIdItem(int index)
    {
        return mDex.protoIds.get(index);
    }
}

package com.yyx.scrollupanddown;


import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xuybin on 2016/12/12
 */
public abstract class CommonAdapterBase<T> extends CommonAdapter<T> {
    public CommonAdapterBase(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }
    @Override
    protected void convert(ViewHolder holder, T t, int position) {
        List<T> dataTest = super.getDatas();
        if(dataTest!=null && t!=null&&position>=0&&position<dataTest.size()){
            convertA(holder,t,position);
        }
    }

    protected abstract void convertA(ViewHolder holder, T t, int position);

}

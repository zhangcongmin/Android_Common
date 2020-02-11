package com.wwzl.commonlib.adapter;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.Collection;
import java.util.List;


/**
 * adapter基类  有些自定义方法在此重写
 * @author weibin.chen
 * @since 2019/4/9 15:38
 */
public abstract class BaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void onItemViewHolderCreated(BaseViewHolder viewHolder, int viewType) {
        // 绑定 view
        DataBindingUtil.bind(viewHolder.itemView);
    }
    public void replaceData(List<T> data) {
        if (data != null) {
            super.replaceData(data);
        } else {
            getData().clear();
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        getData().clear();
        notifyDataSetChanged();
    }

    @Override
    public void addData(T data) {
        if (data != null) {
            super.addData(data);
        }
    }

    @Override
    public void setNewData(List<T> data) {
        super.setNewData(data);
    }

    @Override
    public void addData(Collection<? extends T> newData) {
        if (newData != null) {
            super.addData(newData);
        }
    }

    public int getColor(Context context, int color){
        return context.getResources().getColor(color);
    }

    public int getColor(int color){
        return getContext().getResources().getColor(color);
    }

    public FragmentActivity getBaseActvity(){
            return (FragmentActivity) getContext();
    }

}

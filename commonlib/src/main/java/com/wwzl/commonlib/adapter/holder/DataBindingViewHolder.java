package com.wwzl.commonlib.adapter.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author : zcm
 * time   : 2020/01/03
 * desc   :
 * version: 1.0
 */
public class DataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public DataBindingViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    private T t;

    public T getBinding() {
        if (t == null) {
            t = DataBindingUtil.bind(itemView);
        }
        return t;
    }
}

package com.wwzl.commonlib.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.List;

/**
 * 多布局adpater
 *
 * @author weibin.chen
 * @since 2019/4/10 11:07
 */
public abstract class BaseMultiAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, K> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseMultiAdapter(List<T> data) {
        super(data);
    }

    public void replaceData(List<T> data) {
        if (data != null) {
            super.replaceData(data);
        } else {
            getData().clear();
            notifyDataSetChanged();
        }
    }
    public int getColor(int color){
        return getContext().getResources().getColor(color);
    }
}

package com.wwzl.commonlib.bean;

/**
 * 作者: created by zcm on 2018/11/28
 * 修改: modified by zcm on 2018/11/28
 * 描述: desc(不需要关心result返回数据通用实体)
 */
public class ObjectResult extends BaseResponse<Object>{
    @Override
    public String toString() {
        return "ObjectResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

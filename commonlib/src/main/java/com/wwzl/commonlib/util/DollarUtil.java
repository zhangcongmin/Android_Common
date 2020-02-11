package com.wwzl.commonlib.util;

import java.text.DecimalFormat;

/**
 * author : zcm
 * time   : 2019/12/24
 * desc   :
 * version: 1.0
 */
public class DollarUtil {
    public static String get2PointMoney(float money) {
        return new DecimalFormat("0.00").format(money);
    }

    public static String get2PointMoney(double money) {
        return new DecimalFormat("0.00").format(money);
    }

    public static String get2PointMoney(String money) {
        float num = Float.parseFloat(money);
        return new DecimalFormat("0.00").format(num);
    }
}

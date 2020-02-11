package com.wwzl.commonlib.gson;


import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import com.wwzl.commonlib.application.AppApplication;

public class JsonFileUtils {
    /**
     * 获取去最原始的数据信息
     *
     * @return json data
     */
    public static String parseJson( String jsonFileName) {
        InputStream input = null;
        try {
            input = AppApplication.getApp().getAssets().open(jsonFileName + ".json");
            String json = convertStreamToString(input).replace("\r","").replace("\n","");
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * input 流转换为字符串
     *
     * @param is
     * @return
     */
    private static String convertStreamToString(InputStream is) {
        String s = null;
        try {
            //格式转换
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) {
                s = scanner.next();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
package com.wwzl.commonlib.arouter;

/**
 * author : zcm
 * time   : 2019/12/25
 * desc   :
 * version: 1.0
 */
public interface RouterUrl {
    interface Category{
        String BASE = "/category";
        String HOME= BASE+"/home";
        String MINE= BASE+"/mine";
        String SEARCH = BASE + "/search";
        String SEARCH_RESULT = BASE + "/search_result";
        String CATEGORY_LIST = BASE + "/category_list";
        String GOODS_DETAIL = BASE + "/goods_detail";
        String VIDEO_PLAY = BASE + "/video_play";
        String GOODS_LIST = BASE + "/goods_list";
        String PRODUCT_MOREPICTURE = BASE + "/product_more_picture";
        String NEWS_LIST = BASE + "/news_list";
        String WEB_VIEW = BASE + "/web_view";
        String VIDEO_LIST = BASE + "/video_list";
        String BAOJIA = BASE + "/baojia";
        String PRODUCT_PARAMETER = BASE + "/product_parameter";
        interface Key{
            String searchKey="search_key";
            String parcelable="parcelable";
            String id = "id";
        }
    }

    interface App{
        String BASE = "/app";
        String TEST= BASE+"/test";
    }
}

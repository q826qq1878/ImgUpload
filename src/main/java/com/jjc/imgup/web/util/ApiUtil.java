package com.jjc.imgup.web.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by jiangjiacheng on 2017/9/18.
 */
public class ApiUtil {


    public static JSONObject getSuccess(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ret","200");
        return  jsonObject;
    }

    public static JSONObject getFail(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ret","400");
        return  jsonObject;
    }

}

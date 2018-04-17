package com.jjc.imgup.web.controller;

import com.jjc.imgup.service.UploadService;
import com.jjc.imgup.web.util.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jiangjiacheng on 2017/9/18.
 * 上传图片的控制层
 */
@Controller
@RequestMapping("/upLoad")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    //处理第一次OPTIONS验证请求
    @RequestMapping(value="/fileUpload", method = RequestMethod.OPTIONS)
    public void webUploader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Credentials", "false");
        response.setHeader("Access-Control-Allow-Origin", "*"); //跨域地址
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
    }

    /**
     *图片上传_临时区域 upload_bak
     * @param id
     * @param size
     * @param type
     * @param file
     * @param request
     */
    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public Object fileUpload(String systemName,String id, String name, String size, String type, @RequestParam("file")MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        //设置跨域
        response.setHeader("Access-Control-Allow-Credentials", "false");
        response.setHeader("Access-Control-Allow-Origin", "*"); //跨域地址
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            return  uploadService.uploadImg(systemName,id,name,size,type,file,request);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiUtil.getFail();
        }
    }

    /**
     *图片上传_临时区域转正式区域
     *
     */
    @RequestMapping(value = "/uploadSuccess",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadSuccess(String fileName,HttpServletRequest request){
        try {
            return  uploadService.uploadSuccess(fileName,request);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiUtil.getFail();
        }
    }

    /**
     *图片上传_临时区域转正式区域
     *
     */
    @RequestMapping(value = "/demo",method = RequestMethod.GET)
    public String index(){
        return  "index";
    }

}

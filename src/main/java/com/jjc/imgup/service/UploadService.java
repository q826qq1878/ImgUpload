package com.jjc.imgup.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {

	JSONObject uploadImg(String systemName , String id, String name, String size, String type, MultipartFile file, HttpServletRequest request) throws Exception;


	JSONObject uploadSuccess(String fileName, HttpServletRequest request) throws Exception;

	void deleteBakAll();
}

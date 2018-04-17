package com.jjc.imgup.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jjc.imgup.dao.ImgPermanentMapper;
import com.jjc.imgup.po.ImgTempExample;
import com.jjc.imgup.service.UploadService;
import com.jjc.imgup.dao.ImgTempMapper;
import com.jjc.imgup.po.ImgPermanent;
import com.jjc.imgup.po.ImgTemp;
import com.jjc.imgup.web.util.ApiUtil;
import com.jjc.imgup.web.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
    ImgPermanentMapper imgPermanentMapper;

	@Autowired
	ImgTempMapper imgTempMapper;


	//临时区域复制到正式区域
	@Override
	public JSONObject uploadSuccess(String fileName , HttpServletRequest request) throws Exception {

		//将临时表的数据复制到正式表-除了名称、创建时间不复制
		ImgTempExample imgTempExample = new ImgTempExample();
		imgTempExample.or().andImgNameEqualTo(fileName);
		List<ImgTemp> list = imgTempMapper.selectByExample(imgTempExample);
		if(null == list || list.size() == 0){
			JSONObject jsonObject = ApiUtil.getFail();
			return jsonObject;
		}

		//拿到根路径
		String filePathGen = UploadUtil.getRootPath(request);
		//拿到解析完的路径 -->临时的路径
		String fileNamePath0 = filePathGen + UploadUtil.getFilePath(fileName);
		//拿到正式的路径 -->正式储存的路径
		Map<String,String> map = UploadUtil.getEnourPath(request,null,filePathGen,true,fileName);
		//将正式的临时的复制到正式里面去
		Long time = UploadUtil.copyFile(new File(fileNamePath0) , new File(map.get("filePath")));

		JSONObject jsonObject = ApiUtil.getSuccess();
		jsonObject.put("fileName",map.get("fileName"));
		jsonObject.put("httpUrl", UploadUtil.getHttpFilePath(map.get("fileName")));
		ImgTemp imgTemp = list.get(0);
		//储存正式表
		ImgPermanent imgPermanent = new ImgPermanent();

		imgPermanent.setImgName(map.get("fileName"));
		imgPermanent.setImgSize(imgTemp.getImgSize());
		imgPermanent.setSubSystem(imgTemp.getSubSystem());
		imgPermanent.setCreateTime(new Date());
		imgPermanentMapper.insertSelective(imgPermanent);

		System.out.println("解析临时地址:" +  UploadUtil.getFilePath(fileName));
		System.out.println("解析正式地址:" +  UploadUtil.getFilePath(map.get("fileName")));
		return jsonObject;
	}

	@Override
	public void deleteBakAll() {

		//先执行一条清除所有语句的
		imgTempMapper.deleteALL();

		//在删除本地临时文件
		StringBuffer sb = new StringBuffer();
	    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
	    ServletContext servletContext = webApplicationContext.getServletContext();
		String filePathGen = "";
		if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0){
			filePathGen = "/home/imgup/";
		}else{
			filePathGen = servletContext.getRealPath("/");
		}

		sb.append(filePathGen)
		  .append(File.separator)
		  .append(UploadUtil.uploadTempUrl);

		UploadUtil.deleteFile(new File(sb.toString()));
	}

	//上传临时区域
	@Override
	public JSONObject uploadImg(String systemName, String id, String name, String size, String type, MultipartFile file, HttpServletRequest request) throws Exception {

		//根目录
		String filePathGen = UploadUtil.getRootPath(request);

		//进行上传并返回图片的绝对路径
		Map<String,String> map =  UploadUtil.getEnourPath(request,file,filePathGen,false,null);
		String ensourPath = map.get("filePath");

		//解析ensourPath
//		String[] split = ensourPath.split("webapp");
//		String string = split[1].substring(1, split[1].length());

//		String httpUrl = request.getRequestURL().toString().split(request.getContextPath())[0];

//		StringBuffer stringBuffer = new StringBuffer(httpUrl).append("/").append(request.getContextPath()).append("/").append(string);

		JSONObject jsonObject = ApiUtil.getSuccess();
		//图片名称
		jsonObject.put("name",map.get("fileName"));
		//图片完整URL
		jsonObject.put("httpUrl", UploadUtil.getHttpFilePath(map.get("fileName")));
//		jsonObject.put("httpUrl",stringBuffer.toString());
		//图片URL
//		jsonObject.put("url",string);

		//上传完成储存数据库临时表
		ImgTemp imgTemp = new ImgTemp();
		imgTemp.setImgName(map.get("fileName"));
		imgTemp.setSubSystem(systemName);//业务系统名称
		imgTemp.setImgSize(size);
		imgTemp.setCreateTime(new Date());
		imgTempMapper.insertSelective(imgTemp);

		return jsonObject;
	}


}

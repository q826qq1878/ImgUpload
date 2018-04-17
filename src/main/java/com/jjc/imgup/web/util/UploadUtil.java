package com.jjc.imgup.web.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by jiangjiacheng on 2017/8/9.
 * 上传相关的方法
 */
@Component
public class UploadUtil {

    // 允许上传图片的格式
    private static final String[] IMAGE_TYPE = { ".jpg", ".jpeg", ".bmp", ".png", "gif" };

    public static final String aesKey = "jiangjiacheng";

    public static final String uploadTempUrl = "upload_bak/images";

    public static final String uploadPersistenceUrl = "upload/images";

    public static String httpUrl = "http://localhost:8080/ImgUpload/";



//    @Value("#{uploadProperties['aesKey']}")
//    public void setAesKey(String taesKey)
//    {
//        aesKey = taesKey;
//    }
//    @Value("#{uploadProperties['uploadTemp']}")
//    public void setUploadTempUrl(String tuploadTempUrl) {
//        uploadTempUrl = tuploadTempUrl;
//    }
//    @Value("#{uploadProperties['uploadPersistence']}")
//    public void setUploadPersistenceUrl(String tuploadPersistenceUrl) {
//        uploadPersistenceUrl = tuploadPersistenceUrl;
//    }

    //递归删除文件夹
    public static void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
    }

    //根据key进行AES加密 //在使用URL进行编码
    public static  String aesEncrypt(String content) throws Exception {
        return URLEncoder.encode(AESUtil.aesEncrypt(content, aesKey) , "utf-8").replaceAll("%","hcjjc");
    }

    ///在使用URL进行解码 // 根据key进行AES解密
    public static  String aesDecrypt(String content) throws Exception {
        return AESUtil.aesDecrypt(URLDecoder.decode(content.replaceAll("hcjjc","%"), "utf-8"), aesKey);
    }

    public static String getRootPath(HttpServletRequest request){
        String filePathGen = null;
        /**如果当前环境为linux操作系统，将文件上传到指定目录*/
        if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0){
            filePathGen = "/home/imgup/";
        }else{
            filePathGen = request.getSession().getServletContext().getRealPath("/");
        }
        return  filePathGen;
    }

    //根据文件名称解析出url
    public static String getFilePath(String parmFileName) throws Exception {

        if(!StringUtil.isEmpty(parmFileName)){

            String tfileName =parmFileName.split("\\.")[0];
            String tfileNameExt =parmFileName.split("\\.")[1];

            //解密
            String fileName = aesDecrypt(tfileName);

            String [] fileNames = fileName.split("_");

            StringBuffer sb = new StringBuffer();
            //拼接upload/img目录
            sb.append(fileNames[0].equals("1") ? uploadPersistenceUrl : uploadTempUrl).append(File.separator);
            //拼接年月日的路径
            String[] dates = StringUtil.passDateStr(fileNames[1]);
            sb.append(dates[0]).append(File.separator);
            sb.append(dates[1]).append(File.separator);
            sb.append(dates[2]).append(File.separator);
            //拼接自己的名称
            sb.append(tfileName);
            sb.append(".").append(tfileNameExt);
            return sb.toString();
        }else{
            return  null;
        }
    }

    //复制文件
    public static long copyFile(File f1,File f2) throws Exception{
        long time=System.currentTimeMillis();
        int length=2097152;
        FileInputStream in=new FileInputStream(f1);
        FileOutputStream out=new FileOutputStream(f2);
        byte[] buffer=new byte[length];
        while(true){
            int ins=in.read(buffer);
            if(ins==-1){
                in.close();
                out.flush();
                out.close();
                return System.currentTimeMillis()-time;
            }else{
                out.write(buffer,0,ins);
            }

        }
    }

    //写入文件
    public static Map<String,String> getEnourPath(HttpServletRequest request, MultipartFile file, String filePathGen, boolean falg,String fileName) throws Exception {
        Boolean flag = false;
//		System.out.println(file.getOriginalFilename());

        String ImgName = new String();
        //根据falg判断是上传临时 还是把 临时复制成正式的图片
        if(falg){//复制
            ImgName = fileName;
        }else{//临时上传
            //拼接名字
            String typeName  =  file.getContentType();
            if("multipart/form-data".equals(typeName)){
                ImgName = file.getOriginalFilename();
            }else{
                int pos = typeName.lastIndexOf("/");
                if(pos == -1) {
                    pos = typeName.lastIndexOf("\\");
                }
                typeName = typeName.substring(pos+1,typeName.length());
                ImgName = "tempImgName"+System.currentTimeMillis()+"."+typeName;
            }
        }

        //校验图片格式
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(ImgName, type)) {
                flag = true;
                break;
            }
        }
        //如果图片校验错误，直接返回。
        if (!flag) {
            return null;
        }
        //生成图片的绝对路径
        Map<String,String> files = getFilePath(request,ImgName,filePathGen,falg);

        //创建File对象
        File newfile = new File(files.get("filePath"));

        //把图片写入到磁盘中
        try {
            if(!falg){
                file.transferTo(newfile);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return files;
    }

    //拼接思路使用 0_年月日_UUID 形成字符串  在使用AES算法用指定KEY进行加密 ，加密后进行URL编码--》形成文件名称。
    //0 代表 临时文件夹   1代表正式文件夹
    //解析的时候先使用URL进行解码-》在使用AES算法用指定KEY进行解密
    // 在按照_进行劈开  拿到 / 年 / 月 / 日 / 年月日_UUID.文件名后缀
    private static Map<String,String> getFilePath(HttpServletRequest request, String originalFilename, String filePath , boolean falg) throws Exception {

        String year  = Integer.toString(DateUtilJJC.getYear(new Date()));
        String month = StringUtil.dateCompletion(Integer.toString(DateUtilJJC.getMonth(new Date())));
        String day   = StringUtil.dateCompletion(Integer.toString(DateUtilJJC.getDay(new Date())));
        UUID uuid = UUID.randomUUID();

        String fileFolder = filePath +  (falg ? uploadPersistenceUrl: uploadTempUrl  ) + File.separator + year + File.separator +
                month + File.separator +
                day;
        File file = new File(fileFolder);
        //如果文件目录不存在，则进行创建
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        //生成图片的文件名 未加密前
        StringBuffer fileName = new StringBuffer(falg ? "1" : "0")
                .append("_")
                .append(year).append(month).append(day)
                .append("_")
                .append(uuid.toString());
        //使用AES方式进行加密
        String fileNameAES = UploadUtil.aesEncrypt(fileName.toString());

        StringBuffer fileNameSB = new StringBuffer(fileNameAES)
                .append(".")
                .append(StringUtils.substringAfterLast(originalFilename, "."));
        //拼接图片的路径
        Map<String,String> map = new HashMap<>();
        map.put("filePath",fileFolder + File.separator + fileNameSB.toString());
        map.put("fileName",fileNameSB.toString());
        return map;
    }


    public static String getHttpFilePath(String parmFileName) throws Exception {
        if(StringUtils.isEmpty(parmFileName)){
            return  null;
        }
        String path = getFilePath(parmFileName);

        return  httpUrl+path;
    }

    public static void main(String[] args) throws Exception {
        String s = getFilePath("QdWtJ8ghwFlQNyA7mLUlj1H3sOKLuPr%2BALnqbs1k6Oy0mGK8qzB8Eb6JIFP0%2FDA0.png");
        System.out.println(s);
    }


}

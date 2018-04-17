package com.jjc.imgup.job;

import com.jjc.imgup.service.UploadService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jiangjiacheng on 2017/9/18.
 */
@Component
public class FileBakJob {

    private static Logger logger = Logger.getLogger(FileBakJob.class);

    @Autowired
    private UploadService uploadService;


    //每天凌晨3点。清除数据库表、清除临时文件夹。
    @Scheduled(cron = "0 0 3 * * ?")
    //每天5分钟。清除数据库表、清除临时文件夹。
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void TaskJob() {
        logger.info("清除数据库临时表、临时文件夹---开始-----------------");
        try {
            uploadService.deleteBakAll();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("清除数据库临时表、临时文件夹---开始-----------------");
            logger.error(e);
            logger.error("清除数据库临时表、临时文件夹---结束-----------------");
        }
        logger.info("清除数据库临时表、临时文件夹---结束-----------------");
    }



}

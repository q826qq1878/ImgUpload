package com.jjc.imgup.dao;

import com.jjc.imgup.po.ImgTempExample;
import com.jjc.imgup.po.ImgTemp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImgTempMapper {
    int countByExample(ImgTempExample example);

    int deleteByPrimaryKey(Integer id);
    int deleteALL();

    int insert(ImgTemp record);

    int insertSelective(ImgTemp record);

    ImgTemp selectByPrimaryKey(Integer id);

    List<ImgTemp> selectByExample(ImgTempExample example);

    int updateByExampleSelective(@Param("record") ImgTemp record, @Param("example") ImgTempExample example);

    int updateByExample(@Param("record") ImgTemp record, @Param("example") ImgTempExample example);

    int updateByPrimaryKeySelective(ImgTemp record);

    int updateByPrimaryKey(ImgTemp record);
}
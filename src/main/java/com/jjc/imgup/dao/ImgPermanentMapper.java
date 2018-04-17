package com.jjc.imgup.dao;


import com.jjc.imgup.po.ImgPermanentExample;
import com.jjc.imgup.po.ImgPermanent;

import java.util.List;

public interface ImgPermanentMapper {
    int countByExample(ImgPermanentExample example);

    int deleteByExample(ImgPermanentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ImgPermanent record);

    int insertSelective(ImgPermanent record);

    List<ImgPermanent> selectByExample(ImgPermanentExample example);

    ImgPermanent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImgPermanent record);

    int updateByPrimaryKey(ImgPermanent record);
}
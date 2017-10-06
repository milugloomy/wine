package com.wine.base.dao;

import java.util.List;

import com.wine.base.bean.Image;

public interface ImageMapper {
    int insert(Image record);

    Image selectByPrimaryKey(Integer imgId);
    
    List<Image> selectByProductId(Integer productId);
    
    void deleteByProductId(Integer productId);

}
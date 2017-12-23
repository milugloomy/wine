package com.wine.base.dao;

import com.wine.base.bean.ImageSetting;

public interface ImageSettingMapper {
    int insert(ImageSetting record);

    ImageSetting select();
    
    int updateByPrimaryKeySelective(ImageSetting record);
}
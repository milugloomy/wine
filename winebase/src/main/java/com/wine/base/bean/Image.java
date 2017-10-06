package com.wine.base.bean;

public class Image {
    private Integer imgId;

    private Integer productId;

    private String imgUrl;

    private Integer mainPic;

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getMainPic() {
        return mainPic;
    }

    public void setMainPic(Integer mainPic) {
        this.mainPic = mainPic;
    }
}
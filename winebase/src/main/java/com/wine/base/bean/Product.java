package com.wine.base.bean;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Product {
	
    private Integer productId;
    
    private String productType;

    private Double originPrice;

    private Double vipPrice;

    private String productName;

    private Integer totalQuantity;

    private Integer remain;

    private String status;
    @JSONField (format="yyyy-MM-dd")  
    private Date addTime;
    
    private String imgUrl;
    
    private List<Image> imgList; 
    private List<Image> detailImgList; 
    private List<Param> paramList; 
    
    private String pp;//品牌
    private String bzfs;//包装方式
    private String tl;//糖量
    private String zl;//种类
    private String ptpz;//葡萄品种
    private String nf;//年份
    private String sych;//适用场合
    private String nzgy;//酿造工艺
    private String kg;//口感
    private String gfj;//高分酒
    private String dpcy;//搭配菜肴
    private String xq;//香气
    private String jkfs;//进口方式
    private String xjsj;//醒酒时间
    private String cpcd;//产品产地

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<Image> getImgList() {
		return imgList;
	}

	public void setImgList(List<Image> imgList) {
		this.imgList = imgList;
	}

	public List<Image> getDetailImgList() {
		return detailImgList;
	}

	public void setDetailImgList(List<Image> detailImgList) {
		this.detailImgList = detailImgList;
	}

	public String getPp() {
		return pp;
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	public String getBzfs() {
		return bzfs;
	}

	public void setBzfs(String bzfs) {
		this.bzfs = bzfs;
	}

	public String getTl() {
		return tl;
	}

	public void setTl(String tl) {
		this.tl = tl;
	}

	public String getZl() {
		return zl;
	}

	public void setZl(String zl) {
		this.zl = zl;
	}

	public String getPtpz() {
		return ptpz;
	}

	public void setPtpz(String ptpz) {
		this.ptpz = ptpz;
	}

	public String getNf() {
		return nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}

	public String getSych() {
		return sych;
	}

	public void setSych(String sych) {
		this.sych = sych;
	}

	public String getNzgy() {
		return nzgy;
	}

	public void setNzgy(String nzgy) {
		this.nzgy = nzgy;
	}

	public String getKg() {
		return kg;
	}

	public void setKg(String kg) {
		this.kg = kg;
	}

	public String getGfj() {
		return gfj;
	}

	public void setGfj(String gfj) {
		this.gfj = gfj;
	}

	public String getDpcy() {
		return dpcy;
	}

	public void setDpcy(String dpcy) {
		this.dpcy = dpcy;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getJkfs() {
		return jkfs;
	}

	public void setJkfs(String jkfs) {
		this.jkfs = jkfs;
	}

	public String getXjsj() {
		return xjsj;
	}

	public void setXjsj(String xjsj) {
		this.xjsj = xjsj;
	}

	public String getCpcd() {
		return cpcd;
	}

	public void setCpcd(String cpcd) {
		this.cpcd = cpcd;
	}

	public List<Param> getParamList() {
		return paramList;
	}

	public void setParamList(List<Param> paramList) {
		this.paramList = paramList;
	}
	
}
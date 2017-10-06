package com.wine.base.bean;

import java.util.Date;

public class BackJnl {
    private Integer bjnlno;

    private String userid;

    private String transcode;

    private Date transtime;

    private String returncode;

    private String returnmsg;

    private String clientip;

    public Integer getBjnlno() {
        return bjnlno;
    }

    public void setBjnlno(Integer bjnlno) {
        this.bjnlno = bjnlno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }

    public Date getTranstime() {
        return transtime;
    }

    public void setTranstime(Date transtime) {
        this.transtime = transtime;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getReturnmsg() {
        return returnmsg;
    }

    public void setReturnmsg(String returnmsg) {
        this.returnmsg = returnmsg;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }
}
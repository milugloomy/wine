package com.wine.base.bean;

import java.util.Date;

public class Jnl {
    private Integer jnlno;

    private String transcode;

    private String transtype;

    private Date transtime;

    private Date returntime;

    private String returncode;

    private String returnmsg;

    private Integer userseq;

    private String clientip;

    private String channel;

    public Integer getJnlno() {
        return jnlno;
    }

    public void setJnlno(Integer jnlno) {
        this.jnlno = jnlno;
    }

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public Date getTranstime() {
        return transtime;
    }

    public void setTranstime(Date transtime) {
        this.transtime = transtime;
    }

    public Date getReturntime() {
        return returntime;
    }

    public void setReturntime(Date returntime) {
        this.returntime = returntime;
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

    public Integer getUserseq() {
        return userseq;
    }

    public void setUserseq(Integer userseq) {
        this.userseq = userseq;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
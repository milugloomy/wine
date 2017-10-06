package com.wine.base.bean;

import java.util.Date;

public class PayJnl {
    private Integer pjnlno;

    private Double amount;

    private Integer userseq;

    private Integer activityid;

    private String paystatus;

    private Date paytime;

    private Date sendtime;

    private Integer addrseq;

    private String delicomp;

    private String delino;

    public Integer getPjnlno() {
        return pjnlno;
    }

    public void setPjnlno(Integer pjnlno) {
        this.pjnlno = pjnlno;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getUserseq() {
        return userseq;
    }

    public void setUserseq(Integer userseq) {
        this.userseq = userseq;
    }

    public Integer getActivityid() {
        return activityid;
    }

    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getAddrseq() {
        return addrseq;
    }

    public void setAddrseq(Integer addrseq) {
        this.addrseq = addrseq;
    }

    public String getDelicomp() {
        return delicomp;
    }

    public void setDelicomp(String delicomp) {
        this.delicomp = delicomp;
    }

    public String getDelino() {
        return delino;
    }

    public void setDelino(String delino) {
        this.delino = delino;
    }
}
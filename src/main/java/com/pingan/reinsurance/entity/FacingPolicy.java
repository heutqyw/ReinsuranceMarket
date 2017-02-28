package com.pingan.reinsurance.entity;

/**
 * Created by heutqyw on 2017/2/21.
 * 保单临分
 */
public class FacingPolicy {
    //保单号
    private int policyNo;
    //保单名称
    private String policyName;
    //创建人
    private String createdBy;
    //临分状态 A待处理 B已处理 C已完成
    private int cededStatus;
    private String begineTime;
    private String endTime;
    //自留保额
    private int ownAmount;
    //自留比例
    private int ownRate;
    //再保保额
    private int riAmount;
    //描述
    private String description;

    //可保额度
    private int insurableAmount;

    public int getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(int policyNo) {
        this.policyNo = policyNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getCededStatus() {
        return cededStatus;
    }

    public void setCededStatus(int cededStatus) {
        this.cededStatus = cededStatus;
    }

    public String getBegineTime() {
        return begineTime;
    }

    public void setBegineTime(String begineTime) {
        this.begineTime = begineTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getOwnAmount() {
        return ownAmount;
    }

    public void setOwnAmount(int ownAmount) {
        this.ownAmount = ownAmount;
    }

    public int getOwnRate() {
        return ownRate;
    }

    public void setOwnRate(int ownRate) {
        this.ownRate = ownRate;
    }

    public int getRiAmount() {
        return riAmount;
    }

    public void setRiAmount(int riAmount) {
        this.riAmount = riAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public int getInsurableAmount() {
        return insurableAmount;
    }

    public void setInsurableAmount(int insurableAmount) {
        this.insurableAmount = insurableAmount;
    }

}

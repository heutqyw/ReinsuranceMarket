package com.pingan.reinsurance.entity;

/**
 * Created by heutqyw on 2017/2/21.
 * 临分合约
 */
public class FacingTreaty {
    //临分合同id
    private String facCode;
    //保单编号
    private String policyCode;
    //再保人代码
    private String reinsurerCode;
    //再保人分出比例
    private int reinsurerCeded;
    //分出保额数目
    private int facAmount;
    //创建时间
    private String createDate;

    public String getFacCode() {
        return facCode;
    }

    public void setFacCode(String facCode) {
        this.facCode = facCode;
    }

    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getReinsurerCode() {
        return reinsurerCode;
    }

    public void setReinsurerCode(String reinsurerCode) {
        this.reinsurerCode = reinsurerCode;
    }

    public int getReinsurerCeded() {
        return reinsurerCeded;
    }

    public void setReinsurerCeded(int reinsurerCeded) {
        this.reinsurerCeded = reinsurerCeded;
    }

    public int getFacAmount() {
        return facAmount;
    }

    public void setFacAmount(int facAmount) {
        this.facAmount = facAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

package com.bigao.backend.module.retention.dto;

/**
 * Created by wait on 2015/12/1.
 */
public class RetentionInfo {
    /** 开服天数 */
    private int openDatesNum;
    /** 注册用户 */
    private int registerNum;
    /** 次日留存 */
    private String secondNum;
    /** 3日留存 */
    private String thirdNum;
    /** 4日留存 */
    private String forthNum;
    /** 5日留存 */
    private String fifthNum;
    /** 6日留存 */
    private String sixthNum;
    /** 7日留存 */
    private String sevenNum;
    /** 10日留存 */
    private String tenNum;
    /** 14日留存 */
    private String fourteenNum;
    /** 21日留存 */
    private String twentyOneNum;
    /** 30日留存 */
    private String thirtyNum;

    public int getOpenDatesNum() {
        return openDatesNum;
    }

    public void setOpenDatesNum(int openDatesNum) {
        this.openDatesNum = openDatesNum;
    }

    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public String getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(String secondNum) {
        this.secondNum = secondNum;
    }

    public String getThirdNum() {
        return thirdNum;
    }

    public void setThirdNum(String thirdNum) {
        this.thirdNum = thirdNum;
    }

    public String getForthNum() {
        return forthNum;
    }

    public void setForthNum(String forthNum) {
        this.forthNum = forthNum;
    }

    public String getFifthNum() {
        return fifthNum;
    }

    public void setFifthNum(String fifthNum) {
        this.fifthNum = fifthNum;
    }

    public String getSixthNum() {
        return sixthNum;
    }

    public void setSixthNum(String sixthNum) {
        this.sixthNum = sixthNum;
    }

    public String getSevenNum() {
        return sevenNum;
    }

    public void setSevenNum(String sevenNum) {
        this.sevenNum = sevenNum;
    }

    public String getTenNum() {
        return tenNum;
    }

    public void setTenNum(String tenNum) {
        this.tenNum = tenNum;
    }

    public String getFourteenNum() {
        return fourteenNum;
    }

    public void setFourteenNum(String fourteenNum) {
        this.fourteenNum = fourteenNum;
    }

    public String getTwentyOneNum() {
        return twentyOneNum;
    }

    public void setTwentyOneNum(String twentyOneNum) {
        this.twentyOneNum = twentyOneNum;
    }

    public String getThirtyNum() {
        return thirtyNum;
    }

    public void setThirtyNum(String thirtyNum) {
        this.thirtyNum = thirtyNum;
    }

    @Override
    public String toString() {
        return "RetentionInfo{" +
                "openDatesNum=" + openDatesNum +
                ", registerNum=" + registerNum +
                ", secondNum=" + secondNum +
                ", thirdNum=" + thirdNum +
                ", forthNum=" + forthNum +
                ", fifthNum=" + fifthNum +
                ", sixthNum=" + sixthNum +
                ", sevenNum=" + sevenNum +
                ", tenNum=" + tenNum +
                ", fourteenNum=" + fourteenNum +
                ", twentyOneNum=" + twentyOneNum +
                ", thirtyNum=" + thirtyNum +
                '}';
    }
}

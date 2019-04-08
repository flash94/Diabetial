package com.hayroyal.tom.diabetial;


/**
 * Created by robot on 5/14/18.
 */

public class TestResult {
    private int mID;
    private String mBodyweight;
    private String mBloodsugar;
    private String mBloodpressure;
    private String mDate;
    private String mTime;
    private String mTitle;
    private String mCholesterol;
    private String mActive;
    public TestResult(int mID, String mTitle, String mBodyweight, String mBloodpressure, String mBloodsugar, String mDate, String mTime, String mCholesterol, String mActive) {
        this.mTitle = mTitle;
        this.mID = mID;
        this.mBodyweight = mBodyweight;
        this.mBloodpressure = mBloodpressure;
        this.mBloodsugar = mBloodsugar;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mCholesterol = mCholesterol;
        this.mActive = mActive;
    }

    public TestResult(){

    }

    public TestResult(String mTitle, String mBodyweight, String mBloodpressure, String mBloodsugar, String mDate, String mTime, String mCholesterol, String mActive) {
        this.mTitle = mTitle;
        this.mBodyweight = mBodyweight;
        this.mBloodpressure = mBloodpressure;
        this.mBloodsugar = mBloodsugar;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mCholesterol = mCholesterol;
        this.mActive = mActive;
    }


    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getBodyweight() {
        return mBodyweight;
    }

    public void setBodyweight(String mBodyweight) {
        this.mBodyweight = mBodyweight;
    }

    public String getBloodpressure() {
        return mBloodpressure;
    }

    public void setBloodpressure(String mBloodpressure) {
        this.mBloodpressure = mBloodpressure;
    }

    public String getBloodsugar() {
        return mBloodsugar;
    }

    public void setBloodsugar(String mBloodsugar) {
        this.mBloodsugar = mBloodsugar;
    }

    public String getCholesterol() {
        return mCholesterol;
    }

    public void setCholesterol(String mCholesterol) {
        this.mCholesterol = mCholesterol;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getActive() {
        return mActive;
    }

    public void setActive(String mActive) {
        this.mActive = mActive;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "mID=" + mID +
                ", mBodyweight='" + mBodyweight + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mBloodpressure='" + mBloodpressure + '\'' +
                ", mBloodsugar='" + mBloodsugar + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mCholesterol='" + mCholesterol + '\'' +
                ", mActive='" + mActive + '\'' +
                '}';
    }
}


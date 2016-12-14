package net.gondor.vo;

import java.util.Date;
import java.util.List;

/**
 * 1.Who
 * 2.When
 * 3.Where
 * 4.What
 * * Created by 206-017 on 2016-12-07.
 */

public class AttendanceVO {

    private String id;
    private String userId;
    private List<Date> dateTime;
    private String lectureId;
    private String classification;
    //시간별 구분을 보관하는 스키마가 필요
    //로그

    /**
     *   private String userId;
     *   private String lectureId;
     *    private String dateTime;
     *     private String dateTime;
     *   private String classification;
     * 기록
     */


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}

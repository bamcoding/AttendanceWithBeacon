package net.gondor.vo;

import java.util.Date;

/**
 * 1.Who
 * 2.When
 * 3.Where
 * 4.What
 * 5.Attendance Result
 * * Created by 206-017 on 2016-12-07.
 */
public class AttendanceVO {

    private String id;
    private UserVO user;
    private Date checkedDateTime;
    private LectureVO lecture;
    private String Classification;

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public LectureVO getLecture() {
        return lecture;
    }

    public void setLecture(LectureVO lecture) {
        this.lecture = lecture;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Date getCheckedDateTime() {
        return checkedDateTime;
    }
    public void setCheckedDateTime(Date checkedDateTime) {
        this.checkedDateTime = checkedDateTime;
    }

    public String getClassification() {
        return Classification;
    }
    public void setClassification(String classification) {
        Classification = classification;
    }
}

package net.gondor.vo;

/**
 * 1.Who
 * 2.When
 * 3.Where
 * 4.What
 * 5.Attendance Result
 * * Created by 206-017 on 2016-12-07.
 */

public class AttendanceVO {

    //필수값
    private String userName;
    private String attendanceResult;
    private String nowDate;

    //null 허용
    private String lecturePlace;
    private String lectureTitle;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getLecturePlace() {
        return lecturePlace;
    }

    public void setLecturePlace(String lecturePlace) {
        if(lecturePlace==null||lecturePlace.length()==0)
        {
            lecturePlace = " ";
        }
        this.lecturePlace = lecturePlace;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        if(lectureTitle==null||lectureTitle.length()==0)
        {
            lectureTitle = " ";
        }
        this.lectureTitle = lectureTitle;
    }

    public String getAttendanceResult() {
        return attendanceResult;
    }

    public void setAttendanceResult(String attendanceResult) {
        this.attendanceResult = attendanceResult;
    }
}

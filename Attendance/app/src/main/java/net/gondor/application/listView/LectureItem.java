package net.gondor.application.listView;

/**
 * Created by 206-017 on 2016-12-12.
 */

public class LectureItem {
    private String lectureTeacher;
    private String lectureTitle;
    private String lectureDesc;
    private String enterTime;
    private String exitTime;

    public String getLectureTeacher() {
        return lectureTeacher;
    }

    public void setLectureTeacher(String lectureTeacher) {
        this.lectureTeacher = lectureTeacher;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        this.lectureTitle = lectureTitle;
    }

    public String getLectureDesc() {
        return lectureDesc;
    }

    public void setLectureDesc(String lectureDesc) {
        this.lectureDesc = lectureDesc;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }
}

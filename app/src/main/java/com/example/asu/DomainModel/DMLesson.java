package com.example.asu.DomainModel;

import com.example.asu.R;
import com.example.asu.RowDataGateway.Lesson;

import java.util.ArrayList;
import java.util.List;

public class DMLesson {
    //parsing fields
    public int id;
    public String name;
    public String classroom;
    public String type;
    private int time;
    public int day;
    public String teacher;
    //DM fields
    public int pic;
    //data
    public ILesson ilesson;
    public void setILesson() {
        this.ilesson = new ILessonAdapter(this.name, this.classroom, this.type, this.time, this.day, this.teacher);
    }
    //get

    public int getTime() {
        return time;
    }

    //constructors
    public DMLesson() {
    }

    private DMLesson(Lesson lesson) {
        this.id = lesson._id;
        this.name = lesson.name;
        this.classroom = lesson.classroom;
        this.type = lesson.type;
        this.time = lesson.time;
        this.day = lesson.day;
        this.teacher = lesson.teacher;
        this.convertTimeToPic();
    }

    //data methods
    public static List<DMLesson> findByDay(int day) {
        List<Lesson> lessons = Lesson.findByDay(day);
        List<DMLesson> res = new ArrayList<>();
        try {
            for (Lesson lesson : lessons) {
                res.add(new DMLesson(lesson));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.add(new DMLesson(new Lesson("Нет занятий", "", "", 0, day,"")));
            return res;
        }
    }

    public static void deleteAll(){
        Lesson.deleteAll();
    }
    //DM methods

    private void convertTimeToPic() {
        switch (this.time) {
            case 1:
                this.pic = R.drawable.para1;
                break;
            case 2:
                this.pic = R.drawable.para2;
                break;
            case 3:
                this.pic = R.drawable.para3;
                break;
            case 4:
                this.pic = R.drawable.para4;
                break;
            case 5:
                this.pic = R.drawable.para5;
                break;
            case 6:
                this.pic = R.drawable.para6;
                break;
            case 7:
                this.pic = R.drawable.para7;
                break;
            default:
                this.pic = 0;
                break;
        }
    }

    @Override
    public String toString() {
        return "DMLesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classroom='" + classroom + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", day=" + day +
                ", teacher=" + teacher +
                '}';
    }
}

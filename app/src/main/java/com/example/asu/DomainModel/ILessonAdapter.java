package com.example.asu.DomainModel;

import com.example.asu.RowDataGateway.Lesson;

public class ILessonAdapter extends Lesson implements ILesson{

    public ILessonAdapter(String name, String classroom, String type, int time, int day, String teacher) {
        super(name, classroom, type, time, day, teacher);
    }

    @Override
    public void save() {
        super.insert();
    }

    @Override
    public void delete() {
        super.delete();
    }
}

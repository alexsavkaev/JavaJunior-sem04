package ru.gb.sem04.models;

import javax.persistence.*;

import java.util.Random;

@Entity
@Table(name = "courses")

public class Course {
    private static final String[] titles = new String[]{"Java", "C#", "C++", "Python", "JavaScript", "PHP", "C", "C#"};
    private static final Random random = new Random();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int duration;

    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static Course create(){
        return new Course(titles[random.nextInt(titles.length)], random.nextInt(6,19));
    }
    public void updateTitle() {
        this.title = titles[random.nextInt(titles.length)];
    }
    public void updateDuration() {
        this.duration = random.nextInt(6,19);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}

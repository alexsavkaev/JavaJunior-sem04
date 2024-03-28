package ru.gb.sem04.HW;
/*
Создайте базу данных (например, SchoolDB).
В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
Настройте Hibernate для работы с вашей базой данных.
Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
Убедитесь, что каждая операция выполняется в отдельной транзакции.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.gb.sem04.models.Course;



public class Program {
    public static void main(String[] args) {
        try(SessionFactory sessionFactory = new Configuration()
                .configure("hibernate-courses.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()){
            // Новая сессия
            Session session = sessionFactory.getCurrentSession();
            // Начало транзакции
            session.beginTransaction();
            // Создать курс
            Course course = Course.create();
            System.out.println("Курс создан.");
            // Сохранить курс
            session.save(course);
            // Получить курс
            Course retrievedCourse = session.get(Course.class, course.getId());
            System.out.println("Полученный курс: " + retrievedCourse);

            // Обновить курс
            retrievedCourse.updateTitle();
            retrievedCourse.updateDuration();
            System.out.println("Обновленный курс: " + retrievedCourse);
            session.update(retrievedCourse);

            // Удалить курс
            session.delete(retrievedCourse);
            System.out.println("Удаленный курс: " + retrievedCourse);
            // Завершить транзакцию
            session.getTransaction().commit();


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

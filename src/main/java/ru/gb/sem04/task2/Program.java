package ru.gb.sem04.task2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.gb.sem04.models.Student;



public class Program {
    public static void main(String[] args) {
        try(SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {
            //New session
            Session session = sessionFactory.getCurrentSession();
            //Start transaction
            session.beginTransaction();
            //Create object
            Student student = Student.create();
            session.save(student);
            System.out.println("Object saved successfully");
            Student retrievedStudent = session.get(Student.class, student.getId());
            System.out.println(retrievedStudent);

            retrievedStudent.updateName();
            retrievedStudent.updateAge();
            session.update(retrievedStudent);
            System.out.println("Object updated successfully");
            System.out.println(retrievedStudent);
            session.delete(retrievedStudent);
            session.getTransaction().commit();
                }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

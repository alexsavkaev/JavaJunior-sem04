package ru.gb.sem04.task1;

import ru.gb.sem04.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Program {
    private static final Random random = new Random();

    public static void main(String[] args) {
        String url = "jdbc:mysql://students.db:3306/";
        String user = "root";
        String password = "123";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            createDataBase(connection);
            System.out.println("Подключение к базе данных прошло успешно");
            useDataBase(connection);
            System.out.println("Использование базы данных прошло успешно");
            createTable(connection);
            System.out.println("Создание таблицы прошло успешно");

            int count = random.nextInt(5, 11);
            for (int i = 0; i < count; i++) {
                insertData(connection, Student.create());
                System.out.println("Запись " + (i + 1) + " прошла успешно");
            }

            Collection<Student> students = readData(connection);
            for (var student : students)
                System.out.println(student);
            System.out.println("Чтение данных прошло успешно");

            for(var student : students) {
                student.updateAge();
                student.updateName();
                updateData(connection, student);
            }
            System.out.println("Обновление данных прошло успешно");

            students = readData(connection);
            for (var student : students)
                System.out.println(student);
            System.out.println("Чтение данных прошло успешно");

            for (var student : students) {
                deleteData(connection, student.getId());
            }
            System.out.println("Удаление данных прошло успешно");

            students = readData(connection);
            for (var student : students)
                System.out.println(student);
            System.out.println("Чтение данных прошло успешно");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //region Вспомогательные методы
    public static void createDataBase(Connection connection) throws SQLException {
        String createDataBase = "CREATE DATABASE IF NOT EXISTS studentsDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDataBase)) {
            statement.execute();
        }
    }

    private static void useDataBase(Connection connection) throws SQLException {
        String useDataBase = "USE studentsDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDataBase)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS students (id int AUTO_INCREMENT PRIMARY KEY, name varchar(255), age INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTable)) {
            statement.execute();
        }
    }

    private static void insertData(Connection connection, Student student) throws SQLException {
        String insertData = "INSERT INTO students (name, age) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertData)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.executeUpdate();
        }
    }

    private static Collection<Student> readData(Connection connection) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String readData = "SELECT * FROM students;";
        try (PreparedStatement statement = connection.prepareStatement(readData)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                students.add(new Student(id, name, age));
            }
            return students;
        }
    }

    private static void updateData(Connection connection, Student student) throws SQLException {
        String updateData = "UPDATE students SET name = ?, age = ? WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(updateData)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setInt(3, student.getId());
            statement.executeUpdate();
        }
    }
    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteData = "DELETE FROM students WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteData)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
        //endregion

}

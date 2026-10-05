package com.springbootcore1.springJdbcDemo2.repository;

import com.springbootcore1.springJdbcDemo2.model.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {


    private String dbUrl = "jdbc:postgresql://localhost:5432/student_db";
    private String dbUser = "kevalsardhara";
    private String dbPassword = "123456";

    private String dbDriver = "org.postgresql.Driver";

    private JdbcTemplate jdbcTemplate;

    private StudentRowMapper studentRowMapper = new StudentRowMapper();

    private RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    private DataSource dataSource;
//    public StudentRepository(DataSource dataSource) {
//        // Spring creates the object of DataSource byself and injects it into the constructor
//        // Use the HikariCP connection pool provided by Spring Boot by default use the springboot freamwork
//        this.dataSource = dataSource;
//    }
//
//    @PostConstruct
//    public void init() {
//        System.out.println("DataSource initialized successfully: " + dataSource.getClass());
//    }


    public void createStudent(Student student) {

        String sql = "INSERT INTO students(id, name, email, age) VALUES (?, ?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sql, student.getId(), student.getName(), student.getEmail(), student.getAge());
        if (rowAffected > 0) {
            System.out.println("Student created successfully");
        } else {
            System.out.println("Failed to create student");
        }

        /*try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            Class.forName(dbDriver);
            System.out.println("Connection established successfully");

            preparedStatement.setLong(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setInt(4, student.getAge());

            int rowAffected = jdbcTemplate.update(sql, student.getId(), student.getName(), student.getEmail(), student.getAge());
//            String sql = """
//                            INSERT INTO students(id, name, email, age)
//                            VALUES ('%d', '%s','%s', '%d')
//                        """.formatted(student.getId(), student.getName(), student.getEmail(), student.getAge());
//            System.out.println(sql);
//            int result = statement.executeUpdate(sql); // CREATE, INSERT, DELETE
            if (rowAffected > 0) {
                System.out.println("Student created successfully");
            } else {
                System.out.println("Failed to create student");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void updateStudent(Student student, Long id) {
        String sql = "UPDATE students SET name = ?, email = ?, age = ? WHERE id = ?";

        int rowAffected = jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getAge(), id); // CREATE, INSERT, DELETE

        if (rowAffected > 0) {
            System.out.println("Student updated successfully");
        } else {
            System.out.println("Failed to updated student");
        }

        /*try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            Class.forName(dbDriver);
            System.out.println("Connection established successfully");

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setLong(4, id);

            int result = preparedStatement.executeUpdate(); // CREATE, INSERT, DELETE

            if (result > 0) {
                System.out.println("Student updated successfully");
            } else {
                System.out.println("Failed to updated student");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to establish connection");
        }*/
    }

    public void deleteStudentById(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";

        int result = jdbcTemplate.update(sql, id); // CREATE, INSERT, DELETE

        if (result > 0) {
            System.out.println("Student deleted successfully");
        } else {
            System.out.println("Failed to delete student");
        }
        /*try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            Class.forName(dbDriver);

            System.out.println("Connection established successfully");
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate(); // CREATE, INSERT, DELETE

            if (result > 0) {
                System.out.println("Student deleted successfully");
            } else {
                System.out.println("Failed to delete student");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to establish connection");
        }*/
    }

    public Student getStudentById(Long id) {
        String sql = "SELECT * FROM students WHERE id = ?";
//            String sql = "SELECT * FROM students";

        Student result = jdbcTemplate.queryForObject(sql, rowMapper/*studentRowMapper*/, id); // SELECT, READ
        return result;
        /*try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            Class.forName(dbDriver);
            System.out.println("Connection established successfully");

            preparedStatement.setLong(1, id);
            try(
                    ResultSet result = preparedStatement.executeQuery(); // SELECT, READ
            ) {
                result.next();
//                List<String> studentList = new ArrayList<>();
//                while (result.next()) {
//                    Student modifiedStudent = mapToStudent(result);
//                    String studentToString = modifiedStudent.toString();
//                    studentList.add(studentToString);
//                }
//                System.out.println("StudentList : " + studentList);
                return mapToStudent(result);
            } catch(Exception ex){
                ex.printStackTrace();
                System.out.println("Failed to get student");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to establish connection");
            return null;
        }*/
    }

    public List<Student> findAllStudent() {
//        String sql = "SELECT * FROM students WHERE id = ?";
        String sql = "SELECT * FROM students";
        List<Student> studentList = jdbcTemplate.query(sql, rowMapper/*studentRowMapper*/);
        return studentList;
        /*try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            Class.forName(dbDriver);
            System.out.println("Connection established successfully");
            try(
                    ResultSet result = preparedStatement.executeQuery(); // SELECT, READ
            ) {
//                result.next();
                List<Student> studentList = new ArrayList<>();
                while (result.next()) {
                    Student modifiedStudent = mapToStudent(result);
//                    String studentToString = modifiedStudent.toString();
                    studentList.add(modifiedStudent);
                }
                System.out.println("StudentList : " + studentList);
                return studentList;
            } catch(Exception ex){
                ex.printStackTrace();
                System.out.println("Failed to get student");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to establish connection");
            return null;
        }*/
    }

    public void completeCrud(Student student, Long id) throws Exception {
        String sql = "INSERT INTO students (id, name, email, age) VALUES (?, ?, ?, ?)";
//            String sql = "SELECT * FROM students";
//            String sql = "UPDATE students SET name = ? WHERE id = ?";
//        String sql = "DELETE FROM students WHERE id = ?";
        try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            Class.forName(dbDriver);

            System.out.println("Connection established successfully");

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setInt(4, student.getAge());

            boolean result = preparedStatement.execute(); // SELECT, READ
            if (result) {
                ResultSet resultSet = preparedStatement.getResultSet();

                if (resultSet == null) {
                    throw new SQLException("Result set is null");
                }
                List<String> studentList = new ArrayList<>();
                System.out.println("---------------------------------");
                while (resultSet.next()) {
                    Student modifiedStudent = mapToStudent(resultSet);
                    String studentToString = modifiedStudent.toString();
                    studentList.add(studentToString);
                    System.out.println(studentToString);
                }
                System.out.println("---------------------------------");

                System.out.println("StudentList : " + studentList);
            } else {
                int rowsAffected = preparedStatement.getUpdateCount();

                if(rowsAffected == 0) {
                    throw new SQLException("Failed to insert student");
                } else {
                    System.out.println("Student inserted successfully");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Student mapToStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setName(resultSet.getString("name"));
        student.setEmail(resultSet.getString("email"));
        student.setAge(resultSet.getInt("age"));
        return student;
    }
}


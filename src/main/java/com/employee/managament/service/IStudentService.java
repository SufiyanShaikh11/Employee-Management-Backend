package com.employee.managament.service;

import java.util.List;

import com.employee.managament.model.Student;

public interface IStudentService {
    Student addStudent(Student student);
    List<Student> getStudents();
    List<Student> getStudentsByDepartment(String department);  // Added
    Student createStudent(Student student);  // Added
    Student updateStudent(Student student, Long id);
    Student getStudentById(Long id);
    boolean existsByEmail(String email);  // Added
    void deleteStudent(Long id);
}

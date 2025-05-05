package com.employee.managament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.managament.model.Student;
import com.employee.managament.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    boolean existsByEmail(String email);  // Added
    List<Student> findByDepartment(String department);  // Added
}

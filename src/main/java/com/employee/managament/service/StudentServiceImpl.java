package com.employee.managament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.managament.exception.StudentAlreadyExistsException;
import com.employee.managament.exception.StudentNotFoundException;
import com.employee.managament.model.Student;
import com.employee.managament.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    @Override
    public Student addStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentAlreadyExistsException(
                "Student with email " + student.getEmail() + " already exists!");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student createStudent(Student student) {
        return addStudent(student);
    }

    @Override
    public Student updateStudent(Student student, Long id) {
        return studentRepository.findById(id)
            .map(existingStudent -> {
                if (!existingStudent.getEmail().equals(student.getEmail()) && 
                    studentRepository.existsByEmail(student.getEmail())) {
                    throw new StudentAlreadyExistsException(
                        "Email " + student.getEmail() + " already exists!");
                }
                existingStudent.setFirstName(student.getFirstName());
                existingStudent.setLastName(student.getLastName());
                existingStudent.setEmail(student.getEmail());
                existingStudent.setDepartment(student.getDepartment());
                return studentRepository.save(existingStudent);
            })
            .orElseThrow(() -> new StudentNotFoundException(
                "Student with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException(
                "Student with ID " + id + " not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(
                "Student with ID " + id + " not found");
        }
        studentRepository.deleteById(id);
    }
}

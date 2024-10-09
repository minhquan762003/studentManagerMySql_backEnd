package com.example.mySqlSpring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.mySqlSpring.model.ResponseObject;
import com.example.mySqlSpring.model.Student;
import com.example.mySqlSpring.repository.Repository;

import java.util.*;

@org.springframework.stereotype.Service
public class Service implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Autowired
    Repository studenRepository;

    public List<Student> getAllStudents() {
        return studenRepository.findAll();
    }

    public ResponseObject saveStudent(Student student) {
        Student savedStudent = studenRepository.save(student);
        return new ResponseObject("ok", "Add Student successfully", savedStudent);
    }

    public ResponseObject getStudentById(Long id) {
        Optional<Student> foundStudent = studenRepository.findById(id);
        if (foundStudent.isPresent()) {
            return new ResponseObject("ok", "Query student successfully", foundStudent);
        }
        return new ResponseObject("Failed", "No student found", "");
    }

    public ResponseObject deleteStudentById(Long id) {
        boolean exist = studenRepository.existsById(id);
        if (exist) {
            studenRepository.deleteById(id);
            return new ResponseObject("ok", "Delete student successfully", getStudentById(id));
        }

        return new ResponseObject("Failed", "Can not find student with id = " + id + " to delete", "");
    }

    public ResponseObject updateStudentById(Student newStudent, Long id) {
        if (existById(id)) {
            Student updatedStudent = studenRepository.findById(id).map(
                    student -> {
                        student.setName(newStudent.getName());
                        student.setAddress(newStudent.getAddress());
                        student.setClassName(newStudent.getClassName());
                        student.setBirthDay(newStudent.getBirthDay());
                        return studenRepository.save(student);
                    }).orElseGet(() -> {
                        return studenRepository.save(newStudent);
                    });

            return new ResponseObject("ok", "Updated Student", updatedStudent);
        } else {
            return new ResponseObject("Failed", "Update Student Failed", "");
        }

    }

    public boolean existById(Long id) {
        return this.studenRepository.existsById(id);
    }

}

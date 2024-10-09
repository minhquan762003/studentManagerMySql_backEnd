package com.example.mySqlSpring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.example.mySqlSpring.model.ResponseObject;
import com.example.mySqlSpring.model.Student;
import com.example.mySqlSpring.service.Service;

@RestController
// @RequestMapping("/students")
public class Controller {
    @Autowired
    private Service studentService;
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/addStudent")
    ResponseEntity<ResponseObject> createStudent(@RequestBody Student student) {
        ResponseObject responseObject = studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
    @DeleteMapping("/{id}")
        ResponseEntity<ResponseObject> deleteStudentById(@PathVariable Long id) {
        boolean exist = studentService.existById(id);
        if (exist) {
            ResponseObject responseObject = studentService.deleteStudentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Can not find student with id = " + id + " to delete", ""));
    }
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateStudentById(@RequestBody Student newStudent, @PathVariable Long id) {
        ResponseObject responseObject = studentService.updateStudentById(newStudent, id);
        if("Failed".equals(responseObject.getStatus())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);

    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getStudentById(@PathVariable Long id) {
        ResponseObject responseObject = studentService.getStudentById(id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }


}

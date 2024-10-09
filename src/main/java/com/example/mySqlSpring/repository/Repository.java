package com.example.mySqlSpring.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mySqlSpring.model.Student;

public interface Repository extends JpaRepository<Student, Long>{
} 

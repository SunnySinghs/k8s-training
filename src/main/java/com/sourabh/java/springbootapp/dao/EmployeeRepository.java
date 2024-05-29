package com.sourabh.java.springbootapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sourabh.java.springbootapp.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

}

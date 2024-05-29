package com.sourabh.java.springbootapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourabh.java.springbootapp.dao.EmployeeRepository;
import com.sourabh.java.springbootapp.entity.EmployeeEntity;
import com.sourabh.java.springbootapp.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeDao) {
		this.employeeRepository = employeeDao;
	}
	@Override
	public List<Employee> findAll() {		
		List<Employee> employees = new ArrayList<>();
		for(EmployeeEntity entity: employeeRepository.findAll()) {
			System.out.println(entity);
			Employee employee = new Employee();
			employee.setEmail(entity.getEmail());
			employee.setName(entity.getName());
			employees.add(employee);
		}
		
		return employees;
	}

	@Override
	public Employee findById(int id) {
		Optional<EmployeeEntity> result = employeeRepository.findById(id);
		EmployeeEntity entity = null;
		Employee employee = new Employee();
		
		if(result.isPresent()) {
			entity = result.get();
			employee.setEmail(entity.getEmail());
			employee.setName(entity.getName());
		}else {
			throw new RuntimeException("Exception in getting Employee."); 
		}
		
		
		return employee;
	}

	@Override
	public void save(Employee employee) {
		System.out.println(employee);
		EmployeeEntity entity = new EmployeeEntity();
		entity.setEmail(employee.getEmail());
		entity.setName(employee.getName());
		employeeRepository.save(entity);

	}

	@Override
	public void deleteById(int id) {
		employeeRepository.deleteById(id);

	}
	
	@Override
	public long load(int id) {
		return fib(id);
	}
	
	public long fib(int n) {
		if (n < 2)
			return 1;
		return fib(n - 2)+ fib(n - 1);
	}

}

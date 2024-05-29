package com.sourabh.java.springbootapp.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sourabh.java.springbootapp.model.Employee;
import com.sourabh.java.springbootapp.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	EmployeeService employeeService;
	
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@RequestMapping("/employees")
	public List<Employee> findAll(){
		return employeeService.findAll();
	}
	
	@RequestMapping("/employees/{id}")
	public Employee findById(@PathVariable int id){
		Employee employee = employeeService.findById(id);
		
		if(employee==null) {
			throw new RuntimeException("No Employee Found.");
		}
		
		return employee;
	}
	
	@RequestMapping(path = "/employees", method = RequestMethod.POST)
	public Employee save(@RequestBody Employee employee){
		
		employeeService.save(employee);
		
		return employee;
	}
	
	
	@RequestMapping(path = "/employees", method = RequestMethod.PUT)
	public Employee saveOrUpdate(@RequestBody Employee employee){
		employeeService.save(employee);
		
		return employee;
	}
	
	@RequestMapping(path = "/employees/{id}", method = RequestMethod.DELETE)
	public String deleteById(@PathVariable int id){
		employeeService.deleteById(id);
		return "Deleted EmployeeId is:"+id;
	}
	
	@RequestMapping("/load/{id}")
	public long load(@PathVariable int id){
		return employeeService.load(id);
	}
	
}

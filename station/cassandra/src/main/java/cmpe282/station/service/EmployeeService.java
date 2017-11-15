package cmpe282.station.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.cassandra.repository.MapId;

import cmpe282.station.cassandra.entity.Employee;
import cmpe282.station.error.AppException;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	
	public Employee findEmployee(Long id);
	
	public Boolean createEmployee(Employee employee); 
	
	public Employee updateEmployee(Long id, HttpServletRequest req) throws AppException;
	
	public Employee deleteEmployee(Long id);

}

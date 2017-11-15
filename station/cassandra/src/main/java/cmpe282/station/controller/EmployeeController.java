package cmpe282.station.controller;

import static cmpe282.station.config.UrlConstants.EMPLOYEE;
import static cmpe282.station.config.JsonConstants.KEY_EMPLOYEE;
import static cmpe282.station.config.JsonConstants.KEY_EMPLOYEES;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cmpe282.station.cassandra.entity.Employee;
import cmpe282.station.config.JsonResponse;
import cmpe282.station.error.AppException;
import cmpe282.station.service.EmployeeService;

@RestController
public class EmployeeController extends AbstractController {

	@Autowired
	EmployeeService employeeSvc;

	@GetMapping(EMPLOYEE)
	public ResponseEntity<JsonResponse> getEmployees() {
		List<Employee> employeeLst = employeeSvc.getAllEmployees();

		if (employeeLst.isEmpty())
			return notFound();

		return success(KEY_EMPLOYEES, employeeLst);

	}

	@GetMapping(EMPLOYEE + "/{id}")
	public ResponseEntity<JsonResponse> getEmployee(@PathVariable Long id) {
		Employee employee = employeeSvc.findEmployee(id);
		if (employee != null)
			return success(KEY_EMPLOYEE, employee);
		return notFound();
	}

	@PostMapping(EMPLOYEE)
	public ResponseEntity<JsonResponse> createEmployee(@RequestBody Employee employee) {
		if (employeeSvc.createEmployee(employee))
			return created(EMPLOYEE + "/" + employee.getId());
		return conflict();
	}

	@PutMapping(EMPLOYEE + "/{id}")
	public ResponseEntity<JsonResponse> updateEmployee(@PathVariable Long id, HttpServletRequest request)
			throws AppException {
		Employee employee = employeeSvc.updateEmployee(id, request);
		if (employee != null)
			return success(KEY_EMPLOYEE, employee);
		return notFound();
	}

	@DeleteMapping(EMPLOYEE + "/{id}")
	public ResponseEntity<JsonResponse> deleteEmployee(@PathVariable Long id) {
		Employee employee = employeeSvc.deleteEmployee(id);
		if (employee != null) {
			return success(KEY_EMPLOYEE, employee);
		}
		return notFound();
	}

}

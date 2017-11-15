package cmpe282.station.repository;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.repository.CrudRepository;

import cmpe282.station.cassandra.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, MapId> {
}

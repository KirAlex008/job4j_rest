package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.auth.domain.Employee;

import java.util.List;
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}

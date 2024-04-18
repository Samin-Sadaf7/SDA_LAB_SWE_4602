package service;

import entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EmployeeRepository;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public Employee saveEmployee(Employee employee) {
        log.info("Employee info is saved Id: "+ employee.getId());
        return employeeRepository.save(employee);
    }
    public Employee findEmployeeById(String userId) {
        return employeeRepository.findEmployeeById(userId);
    }
}
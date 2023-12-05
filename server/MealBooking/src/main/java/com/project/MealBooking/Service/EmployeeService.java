//package com.project.MealBooking.Service;
//
//import com.project.MealBooking.Entity.Employee;
//import com.project.MealBooking.Exception.DuplicateEmailException;
//import com.project.MealBooking.Exception.ResourceNotFoundException;
//import com.project.MealBooking.Repository.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EmployeeService {
//    private final EmployeeRepository employeeRepository;
//
//    @Autowired
//    public EmployeeService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//
//
//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
//
//    public Employee getEmployeeById(Long employeeId) {
//        return employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
//    }
//
//    //Create Employee
//    public Employee createEmployee(Employee employee) {
//        if (employeeRepository.existsByEmail(employee.getEmail())) {
//            throw new DuplicateEmailException("Email already exists");
//        }
//        return employeeRepository.save(employee);
//    }
//
////    Update Employee
//
//    public Employee updateEmployee(Long employeeId, Employee updatedEmployee) {
//        Employee existingEmployee = getEmployeeById(employeeId);
//
//        existingEmployee.setFirstName(updatedEmployee.getFirstName());
//        existingEmployee.setLastName(updatedEmployee.getLastName());
//        return employeeRepository.save(existingEmployee);
//    }
//
//    //delete employee
//    public void deleteEmployee(Long employeeId) {
//        Employee existingEmployee = getEmployeeById(employeeId);
//        employeeRepository.delete(existingEmployee);
//    }
//}

package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not found, id = ".concat(id.toString()));
        }
        return optionalEmployee.get();
    }

    @Transactional
    @Override
    public void updateAvailableDays(Long employeeId, Set<DayOfWeek> availableDays) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not found, id = ".concat(employeeId.toString()));
        }
        Employee employee = optionalEmployee.get();
        employee.setAvailableDays(availableDays);
    }

    @Override
    public List<Employee> findByAvailableDayAndSkill(DayOfWeek dayOfWeek, Long skillsSize, Set<EmployeeSkill> employeeSkills) {
        return employeeRepository.findAllBySkillsAndAvailableDays(employeeSkills, skillsSize, dayOfWeek);
    }
}

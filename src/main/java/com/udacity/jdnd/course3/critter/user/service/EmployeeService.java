package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.enums.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> findAll();
    Employee findById(Long id);
    void updateAvailableDays(Long employeeId, Set<DayOfWeek> availableDays);
    List<Employee> findByAvailableDayAndSkill(DayOfWeek dayOfWeek, Long skillsSize, Set<EmployeeSkill> employeeSkills);
}

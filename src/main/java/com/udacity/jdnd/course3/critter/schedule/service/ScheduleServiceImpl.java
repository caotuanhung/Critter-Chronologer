package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public Schedule save(Schedule schedule) {
        // Update schedule for employees.
        schedule.getEmployees().forEach(employee -> {
            Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
            if (optionalEmployee.isEmpty()) {
                throw new EmployeeNotFoundException("Employee not found, id = ".concat(String.valueOf(employee.getId())));
            }
            optionalEmployee.get().getSchedules().add(schedule);
        });

        // Update schedule for pets and their owner.
        // Then add all customers to schedule.
        schedule.getPets().forEach(pet -> {
            Optional<Pet> optionalPet = petRepository.findById(pet.getId());
            if (optionalPet.isEmpty()) {
                throw new PetNotFoundException("Pet not found, id = ".concat(String.valueOf(pet.getId())));
            }
            optionalPet.get().getSchedules().add(schedule);

            Customer customer = customerRepository.findById(optionalPet.get().getCustomer().getId()).get();
            customer.getSchedules().add(schedule);

            schedule.getCustomers().add(customer);
        });

        // Save schedule.
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Transactional
    @Override
    public List<Schedule> findAllByEmployeeId(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not found, id = ".concat(employeeId.toString()));
        }
        return optionalEmployee.get().getSchedules();
    }

    @Override
    public List<Schedule> findAllByPetId(Long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if (optionalPet.isEmpty()) {
            throw new PetNotFoundException("Pet not found, id = ".concat(petId.toString()));
        }
        return optionalPet.get().getSchedules();
    }

    @Transactional
    @Override
    public List<Schedule> findAllByCustomerId(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new PetNotFoundException("Customer not found, id = ".concat(customerId.toString()));
        }
        List<Schedule> schedules = optionalCustomer.get().getSchedules();
        return optionalCustomer.get().getSchedules();
    }
}

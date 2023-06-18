package com.udacity.jdnd.course3.critter.schedule.controller;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertToScheduleDTO(scheduleService.save(convertToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream().map(schedule -> convertToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService
                .findAllByPetId(petId)
                .stream()
                .map(schedule -> convertToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService
                .findAllByEmployeeId(employeeId)
                .stream()
                .map(schedule -> convertToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.findAllByCustomerId(customerId);
        return scheduleService
                .findAllByCustomerId(customerId)
                .stream()
                .map(schedule -> convertToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    private Schedule convertToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPets(scheduleDTO.getPetIds().stream().map(id -> {
            Pet pet = new Pet();
            pet.setId(id);
            return pet;
        }).collect(Collectors.toList()));
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map(id -> {
            Employee employee = new Employee();
            employee.setId(id);
            return employee;
        }).collect(Collectors.toList()));
        schedule.getActivities().addAll(scheduleDTO.getActivities());
        return schedule;
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }
}

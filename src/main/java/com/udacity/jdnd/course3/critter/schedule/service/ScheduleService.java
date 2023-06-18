package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    Schedule save(Schedule schedule);

    List<Schedule> findAll();

    List<Schedule> findAllByEmployeeId(Long employeeId);

    List<Schedule> findAllByPetId(Long petId);

    List<Schedule> findAllByCustomerId(Long customerId);
}

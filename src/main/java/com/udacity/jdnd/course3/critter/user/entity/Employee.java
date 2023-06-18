package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.enums.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(
            name = "tbl_employee_skill",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "skill_name")
    private Set<EmployeeSkill> skills = new HashSet<>();
    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(
            name = "tbl_employee_available_day",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private Set<DayOfWeek> availableDays = new HashSet<>();
    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedules = new ArrayList<>();

    public Employee() {
    }

    public Set<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void setSchedule(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}

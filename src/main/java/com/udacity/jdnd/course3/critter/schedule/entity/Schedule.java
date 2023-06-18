package com.udacity.jdnd.course3.critter.schedule.entity;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.enums.EmployeeSkill;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "tbl_schedule_detail",
            joinColumns = {
                    @JoinColumn(name = "schema_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "employee_id", referencedColumnName = "id")
            }
    )
    private List<Employee> employees = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "tbl_schedule_detail",
            joinColumns = {
                    @JoinColumn(name = "schema_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "pet_id", referencedColumnName = "id")
            }
    )
    private List<Pet> pets = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "tbl_schedule_detail",
            joinColumns = {
                    @JoinColumn(name = "schema_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "customer_id", referencedColumnName = "id")
            }
    )
    private List<Customer> customers = new ArrayList<>();
    private LocalDate date;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(
            name = "tbl_schedule_activities",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "activity")
    private Set<EmployeeSkill> activities = new HashSet<>();

    public Schedule() {
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

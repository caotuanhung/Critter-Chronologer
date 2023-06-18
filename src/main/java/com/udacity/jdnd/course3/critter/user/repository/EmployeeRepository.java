package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);

    @Query(
            "SELECT e FROM Employee e " +
                    "JOIN e.skills s " +
                    "WHERE s IN :skills AND :dayOfWeek MEMBER OF e.availableDays " +
                    "GROUP BY e HAVING COUNT(s) = :count")
    List<Employee> findAllBySkillsAndAvailableDays(@Param("skills") Set<EmployeeSkill> skills, @Param("count") Long skillsSize, @Param("dayOfWeek") DayOfWeek dayOfWeek);
}

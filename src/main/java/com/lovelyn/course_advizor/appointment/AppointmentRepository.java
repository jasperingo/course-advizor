package com.lovelyn.course_advizor.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  @Query("SELECT a FROM Appointment a JOIN FETCH a.student s WHERE s.courseAdviser.id = :courseAdviserId")
  List<Appointment> findAllByStudentCourseAdviserId(@Param("courseAdviserId") Long courseAdviserId);

}

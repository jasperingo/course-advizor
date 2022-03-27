package com.lovelyn.course_advizor.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

  boolean existsByCourseAdviserIdAndSessionId(Long courseAdviserId, Long sessionId);

  List<Result> findAllByCourseAdviserIdAndSessionIdAndSemester(Long courseAdviserId, Long sessionId, Result.Semester semester);

}

package com.lovelyn.course_advizor.session;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

  Optional<Session> findByStartedAtAndEndedAt(Integer startedAt, Integer endedAt);

}

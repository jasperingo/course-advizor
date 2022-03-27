package com.lovelyn.course_advizor.call;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CallRepository extends JpaRepository<Call, Long> {

  Optional<Call> findByCallSessionId(String callSessionId);

}

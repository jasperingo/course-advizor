package com.lovelyn.course_advizor.course_adviser;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/course-adviser")
public class CourseAdviserController {

  @Autowired
  private CourseAdviserRepository repository;
  
  @GET
  @Path("/test")
  @Produces(MediaType.APPLICATION_JSON)
  public CourseAdviser test() {

    Optional<CourseAdviser> courseAdviser = repository.findById(1L);

    return courseAdviser.orElse(new CourseAdviser());
  }
  
}

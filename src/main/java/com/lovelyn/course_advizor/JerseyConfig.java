package com.lovelyn.course_advizor;

import com.lovelyn.course_advizor.course_adviser.CourseAdviserController;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
  
  public JerseyConfig() {
    register(CourseAdviserController.class);
  }
}

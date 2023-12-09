package com.champlain.courseservice.utils;

import com.champlain.courseservice.Data.Course;
import com.champlain.courseservice.Presentation.CourseRequestDTO;
import com.champlain.courseservice.Presentation.CourseResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

public class entityDTOutils {


    public static CourseResponseDTO toCourseResponseDTO(Course course){
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        BeanUtils.copyProperties(course, courseResponseDTO);
        return courseResponseDTO;
    }

    public static Course toCourseEntity(CourseRequestDTO courseRequestDTO){
        Course course = new Course();
        BeanUtils.copyProperties(courseRequestDTO, course);
        return  course;
    }
    public static String genUUID(){
        return UUID.randomUUID().toString();
    }

}

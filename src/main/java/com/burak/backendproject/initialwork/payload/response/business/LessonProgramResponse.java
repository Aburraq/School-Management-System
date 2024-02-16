package com.burak.backendproject.initialwork.payload.response.business;

import com.burak.backendproject.initialwork.entity.concretes.business.EducationTerm;
import com.burak.backendproject.initialwork.payload.response.user.StudentResponse;
import com.burak.backendproject.initialwork.payload.response.user.TeacherResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.burak.backendproject.initialwork.entity.concretes.business.Lesson;
import com.burak.backendproject.initialwork.entity.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonProgramResponse {

    private Long lessonProgramId;
    private Day day;
    private LocalTime startTime;
    private LocalTime stopTime;
    private Set<Lesson>lessonName;
    private EducationTerm educationTerm;
    private Set<TeacherResponse>teachers;
    private Set<StudentResponse>student;

}

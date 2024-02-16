package com.burak.backendproject.initialwork.payload.mappers;

import com.burak.backendproject.initialwork.entity.concretes.business.EducationTerm;
import com.burak.backendproject.initialwork.entity.concretes.business.Lesson;
import com.burak.backendproject.initialwork.entity.concretes.business.LessonProgram;
import com.burak.backendproject.initialwork.payload.request.business.LessonProgramRequest;
import com.burak.backendproject.initialwork.payload.response.business.LessonProgramResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
public class LessonProgramMapper {

    public LessonProgram mapLessonProgramRequestToLessonProgram(LessonProgramRequest lessonProgramRequest,
                                                                Set<Lesson>lessonSet, EducationTerm educationTerm){
        return LessonProgram.builder()
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .day(lessonProgramRequest.getDay())
                .lessons(lessonSet)
                .educationTerm(educationTerm)
                .build();
    }

    public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram){
        //TODO how to return teacher and students
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .educationTerm(lessonProgram.getEducationTerm())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLessons())
//        .teachers(lessonProgram.getUsers().stream().filter(x->x.getUserRole().getRoleType().getName().equals("Teacher")).collect(
//            Collectors.toSet()))
                .build();
    }

}

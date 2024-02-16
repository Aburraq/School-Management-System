package com.burak.backendproject.initialwork.payload.response.user;

import com.burak.backendproject.initialwork.payload.response.abstracts.BaseUserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.burak.backendproject.initialwork.entity.concretes.business.LessonProgram;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse extends BaseUserResponse {

    private Set<LessonProgram> lessonProgramSet;

    private int studentNumber;

    private String motherName;

    private String fatherName;

    private boolean isActive;

}

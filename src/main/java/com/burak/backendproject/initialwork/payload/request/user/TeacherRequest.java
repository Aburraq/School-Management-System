package com.burak.backendproject.initialwork.payload.request.user;

import com.burak.backendproject.initialwork.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class TeacherRequest extends BaseUserRequest {

    @NotNull(message = "Please select Lesson Program")
    private Set<Long> lessonProgramIdList;

    @NotNull(message = "Please select isAdvisor Teacher")
    private Boolean isAdvisorTeacher;

}

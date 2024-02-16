package com.burak.backendproject.initialwork.payload.request.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLessonProgramToTeacherRequest {

    @NotNull(message = "Please select lesson program")
    @Size(min=1, message = "Lessons must not be empty")
    private Set<Long> lessonProgramId;

    @NotNull(message = "Please select teacher")
    private Long teacherId;

}

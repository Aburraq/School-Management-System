package com.burak.backendproject.initialwork.entity.concretes.business;

import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.burak.backendproject.initialwork.entity.enums.Day;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
    private LocalTime stopTime;

    //manyToMany relations have always third table
    @ManyToMany
    //we are specifying the table and column names
    @JoinTable(
            name = "lesson_program_lesson",
            joinColumns = @JoinColumn(name = "lessonprogram_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson>lessons;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private EducationTerm educationTerm;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessonProgramList",fetch = FetchType.EAGER)
    private Set<User>users;


    @PreRemove
    private void removeLessonFromUser(){
        users.forEach(user -> user.getLessonProgramList().remove(this));
    }

}

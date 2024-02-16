package com.burak.backendproject.initialwork.payload.mappers;

import com.burak.backendproject.initialwork.entity.concretes.business.EducationTerm;
import com.burak.backendproject.initialwork.entity.concretes.business.StudentInfo;
import com.burak.backendproject.initialwork.payload.response.business.StudentInfoResponse;
import com.burak.backendproject.initialwork.entity.concretes.business.Lesson;
import com.burak.backendproject.initialwork.entity.enums.Note;
import com.burak.backendproject.initialwork.payload.request.business.StudentInfoRequest;
import com.burak.backendproject.initialwork.payload.request.business.StudentInfoUpdateRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class StudentInfoMapper {

    private final UserMapper userMapper;

    public StudentInfo mapStudentInfoRequestToStudentInfo(StudentInfoRequest studentInfoRequest,
                                                          Note note, Double average){

        return StudentInfo.builder()
                .infoNote(studentInfoRequest.getInfoNote())
                .absentee(studentInfoRequest.getAbsentee())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .finalExam(studentInfoRequest.getFinalExam())
                .examAverage(average)
                .letterGrade(note)
                .build();

    }

    public StudentInfoResponse mapStudentInfoToStudentInfoResponse (StudentInfo studentInfo){

        return StudentInfoResponse.builder()
                .lessonName(studentInfo.getLesson().getLessonName())
                .creditScore(studentInfo.getLesson().getCreditScore())
                .isCompulsory(studentInfo.getLesson().getIsCompulsory())
                .educationTerm(studentInfo.getEducationTerm().getTerm())
                .id(studentInfo.getId())
                .absentee(studentInfo.getAbsentee())
                .midtermExam(studentInfo.getMidtermExam())
                .finalExam(studentInfo.getFinalExam())
                .infoNote(studentInfo.getInfoNote())
                .note(studentInfo.getLetterGrade())
                .average(studentInfo.getExamAverage())
                .studentResponse(userMapper.mapUserToStudentResponse(studentInfo.getStudent()))
                .build();
    }

    public StudentInfo mapStudentInfoUpdateRequestToStudentInfo(StudentInfoUpdateRequest studentInfoUpdateRequest,
                                                                Long studentInfoRequestId,
                                                                Lesson lesson,
                                                                EducationTerm educationTerm,
                                                                Note note,
                                                                Double average){

        return StudentInfo.builder()
                .id(studentInfoRequestId)
                .infoNote(studentInfoUpdateRequest.getInfoNote())
                .midtermExam(studentInfoUpdateRequest.getMidtermExam())
                .finalExam(studentInfoUpdateRequest.getFinalExam())
                .absentee(studentInfoUpdateRequest.getAbsentee())
                .lesson(lesson)
                .educationTerm(educationTerm)
                .examAverage(average)
                .letterGrade(note)
                .build();


    }

}

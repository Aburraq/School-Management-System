package com.burak.backendproject.initialwork.service.user;

import com.burak.backendproject.initialwork.payload.mappers.UserMapper;
import com.burak.backendproject.initialwork.payload.messages.SuccessMessages;
import com.burak.backendproject.initialwork.payload.request.business.ChooseLessonProgramRequest;
import com.burak.backendproject.initialwork.payload.request.user.StudentUpdateRequestWithoutPassword;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.payload.response.user.StudentResponse;
import com.burak.backendproject.initialwork.repository.user.UserRepository;
import com.burak.backendproject.initialwork.service.business.LessonProgramService;
import com.burak.backendproject.initialwork.service.helper.MethodHelper;
import com.burak.backendproject.initialwork.service.validator.DateTimeValidator;
import com.burak.backendproject.initialwork.service.validator.UniquePropertyValidator;
import com.burak.backendproject.initialwork.entity.concretes.business.LessonProgram;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.entity.enums.RoleType;
import com.burak.backendproject.initialwork.payload.request.user.StudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final UniquePropertyValidator uniquePropertyValidator;

    private final PasswordEncoder passwordEncoder;

    private final LessonProgramService lessonProgramService;

    private final DateTimeValidator dateTimeValidator;

    private final MethodHelper methodHelper;

    private final UserMapper userMapper;

    public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest) {

        User advisorTeacher = methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());

        methodHelper.checkIsAdvisor(advisorTeacher);

        uniquePropertyValidator.checkDuplicate(
                studentRequest.getUsername(),
                studentRequest.getSsn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail()

        );

        User student = userMapper.mapUserRequestToUser(studentRequest);

        student.setAdvisorTeacherId(advisorTeacher.getId());
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        student.setActive(true);
        student.setIsAdvisor(false);
        student.setStudentNumber(getLastNumber());


        return ResponseMessage.<StudentResponse>builder()
                .returnBody(userMapper.mapUserToStudentResponse(userRepository.save(student)))
                .message(SuccessMessages.STUDENT_SAVE)
                .httpStatus(HttpStatus.OK)
                .build();

    }


    private int getLastNumber(){

        if (!userRepository.findStudent(RoleType.STUDENT)) return 1000;

        return  userRepository.getMaxStudentNumber()+1;



    }

    public ResponseMessage<StudentResponse> addLessonProgram(HttpServletRequest httpServletRequest,
                                                             ChooseLessonProgramRequest request) {

        String username = (String) httpServletRequest.getAttribute("username");
        User loggedInStudent = methodHelper.loadUserByName(username);

        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(request.getLessonProgramId());

        Set<LessonProgram> existingLessonPrograms = loggedInStudent.getLessonProgramList();

        dateTimeValidator.checkLessonPrograms(existingLessonPrograms, lessonPrograms);

        existingLessonPrograms.addAll(lessonPrograms);
        loggedInStudent.setLessonProgramList(existingLessonPrograms);

        User savedStudent = userRepository.save(loggedInStudent);

        return ResponseMessage.<StudentResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_ADD_TO_STUDENT)
                .httpStatus(HttpStatus.OK)
                .returnBody(userMapper.mapUserToStudentResponse(savedStudent))
                .build();

    }

    public ResponseEntity<String> updateStudent(StudentUpdateRequestWithoutPassword studentUpdateRequestWithoutPassword,
                                                HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User student = methodHelper.loadUserByName(username);
        uniquePropertyValidator.checkUniqueProperties(student, studentUpdateRequestWithoutPassword);

        student.setMotherName(studentUpdateRequestWithoutPassword.getMotherName());
        student.setFatherName(studentUpdateRequestWithoutPassword.getFatherName());
        student.setBirthPlace(studentUpdateRequestWithoutPassword.getBirthPlace());
        student.setBirthDay(studentUpdateRequestWithoutPassword.getBirthDay());
        student.setEmail(studentUpdateRequestWithoutPassword.getEmail());
        student.setPhoneNumber(studentUpdateRequestWithoutPassword.getPhoneNumber());
        student.setGender(studentUpdateRequestWithoutPassword.getGender());
        student.setName(studentUpdateRequestWithoutPassword.getName());
        student.setSurname(studentUpdateRequestWithoutPassword.getSurname());
        student.setSsn(studentUpdateRequestWithoutPassword.getSsn());
        userRepository.save(student);

        return ResponseEntity.ok(SuccessMessages.STUDENT_UPDATE);

    }

    public ResponseMessage<StudentResponse> updateStudentForManagers(Long id, StudentRequest studentRequest) {

        User student = methodHelper.isUserExist(id);
        methodHelper.checkRole(student, RoleType.STUDENT );
        uniquePropertyValidator.checkUniqueProperties(student, studentRequest);

        User studentForUpdate = userMapper.mapUserRequestToUser(studentRequest);
        studentForUpdate.setMotherName(studentRequest.getMotherName());
        studentForUpdate.setFatherName(studentRequest.getFatherName());

        User advisorTeacher = methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());
        methodHelper.checkRole(advisorTeacher, RoleType.TEACHER);
        methodHelper.checkIsAdvisor(advisorTeacher);

        studentForUpdate.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        studentForUpdate.setActive(true);
        studentForUpdate.setStudentNumber(student.getStudentNumber());
        studentForUpdate.setId(student.getId());

        return ResponseMessage.<StudentResponse>builder()
                .message(SuccessMessages.STUDENT_UPDATE)
                .returnBody(userMapper.mapUserToStudentResponse(userRepository.save(studentForUpdate)))
                .httpStatus(HttpStatus.OK)
                .build();


    }

    public ResponseMessage changeStatus(Long id, boolean status) {

        User student = methodHelper.isUserExist(id);
        methodHelper.checkRole(student, RoleType.STUDENT);
        student.setActive(status);
        return  ResponseMessage.builder()
                .message("Student is "+(status ? "active" : "passive"))
                .httpStatus(HttpStatus.OK)
                .build();

    }
}

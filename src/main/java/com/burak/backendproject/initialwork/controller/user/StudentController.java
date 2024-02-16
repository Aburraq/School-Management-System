package com.burak.backendproject.initialwork.controller.user;

import com.burak.backendproject.initialwork.payload.request.business.ChooseLessonProgramRequest;
import com.burak.backendproject.initialwork.payload.request.user.StudentUpdateRequestWithoutPassword;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.payload.response.user.StudentResponse;
import com.burak.backendproject.initialwork.payload.request.user.StudentRequest;
import com.burak.backendproject.initialwork.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(@Valid @RequestBody StudentRequest studentRequest){

        return ResponseEntity.ok(studentService.saveStudent(studentRequest));

    }


    @PreAuthorize("hasAnyAuthority('Student')")
    @PatchMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(
            @RequestBody @Valid StudentUpdateRequestWithoutPassword studentUpdateRequestWithoutPassword,
            HttpServletRequest httpServletRequest){

        return studentService.updateStudent(studentUpdateRequestWithoutPassword, httpServletRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Dean', 'ViceDean')")
    @PutMapping("/updateStudentForManagers/{id}")
    public ResponseMessage<StudentResponse> updateStudentForManagers(@PathVariable Long id,
                                                                     @RequestBody @Valid StudentRequest studentRequest){


        return studentService.updateStudentForManagers(id, studentRequest);

    }

    @PreAuthorize("hasAnyAuthority('Student')")
    @PostMapping("/addLessonProgram")
    public ResponseMessage<StudentResponse> addLessonProgram(HttpServletRequest httpServletRequest,
                                                             @Valid @RequestBody ChooseLessonProgramRequest request){

        return studentService.addLessonProgram(httpServletRequest, request);

    }


    @PreAuthorize("hasAnyAuthority('Admin', 'Dean', 'ViceDean')")
    @GetMapping("/changeStatus")
    public ResponseMessage changeStatus(@RequestParam Long id,
                                        @RequestParam boolean status){

        return studentService.changeStatus(id, status);

    }

}

package com.burak.backendproject.initialwork.controller.business;

import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.payload.request.business.StudentInfoRequest;
import com.burak.backendproject.initialwork.payload.request.business.StudentInfoUpdateRequest;
import com.burak.backendproject.initialwork.payload.response.business.StudentInfoResponse;
import com.burak.backendproject.initialwork.service.business.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid StudentInfoRequest studentInfoRequest){

        return studentInfoService.saveStudentInfo(httpServletRequest, studentInfoRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @PutMapping("/update/{studentInfoId}")
    public ResponseMessage<StudentInfoResponse> updateStudentInfo(@Valid @RequestBody StudentInfoUpdateRequest studentInfoUpdateRequest,
                                                                  @PathVariable Long studentInfoId){

        return studentInfoService.updateStudentInfo(studentInfoUpdateRequest, studentInfoId);

    }


    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage delete(@PathVariable Long id){

        return studentInfoService.deleteStudentInfo(id);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/findStudentInfoByPage")
    public Page<StudentInfoResponse> findStudentInfoByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "absentee") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){

        return studentInfoService.findStudentInfoByPage(page, size, sort, type);

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @GetMapping("/getAll/{id}")
    public ResponseEntity<StudentInfoResponse> getStudentInfoById (@PathVariable Long id){

        return ResponseEntity.ok(studentInfoService.findStudentInfoById(id));

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @GetMapping("/getByStudentId/{studentId}")
    public ResponseEntity<List<StudentInfoResponse>> getStudentInfoByStudentId (@PathVariable Long studentId){

        return ResponseEntity.ok(studentInfoService.findStudentInfoByStudentId(studentId));

    }

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @GetMapping("/getAllByTeacher")
    public ResponseEntity<Page<StudentInfoResponse>> getAllByTeacher(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){

        return ResponseEntity.ok(studentInfoService.getAllByTeacher(page, size, httpServletRequest));

    }

    @PreAuthorize("hasAnyAuthority('Student')")
    @GetMapping("/getAllByStudent")
    public ResponseEntity<Page<StudentInfoResponse>> getAllByStudent(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){

        return ResponseEntity.ok(studentInfoService.getAllByStudent(page, size, httpServletRequest));

    }
}

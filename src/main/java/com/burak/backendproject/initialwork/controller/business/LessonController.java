package com.burak.backendproject.initialwork.controller.business;


import com.burak.backendproject.initialwork.payload.request.business.LessonRequest;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.service.business.LessonService;
import com.burak.backendproject.initialwork.entity.concretes.business.Lesson;
import com.burak.backendproject.initialwork.payload.response.business.LessonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping("/save")
    public ResponseMessage<LessonResponse> saveLesson(@Valid @RequestBody LessonRequest lessonRequest){

        return lessonService.saveLesson(lessonRequest);

    }
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteLesson(@PathVariable Long id){

        return lessonService.deleteById(id);

    }


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getLessonByName")
    public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName){

        return lessonService.getLessonByLessonName(lessonName);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/findByPage")
    public Page<LessonResponse> getLessonByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "lessonName") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){

        return lessonService.findLessonByPage(page, size, sort, type);

    }


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/idSet")
    public Set<Lesson> getAllLessonByIdSet(@RequestParam(name = "lessonId") Set<Long> idSet){

        return lessonService.getLessonByIdSet(idSet);

    }


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PutMapping("/update/{lessonId}")
    public ResponseEntity<LessonResponse> updateLessonById(@PathVariable Long lessonId,
                                                           @RequestBody LessonRequest lessonRequest){

        return ResponseEntity.ok(lessonService.updateLessonById(lessonId, lessonRequest));

    }

}

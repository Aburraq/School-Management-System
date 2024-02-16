package com.burak.backendproject.initialwork.controller.business;

import com.burak.backendproject.initialwork.payload.request.business.MeetingRequest;
import com.burak.backendproject.initialwork.payload.response.business.MeetingResponse;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.service.business.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/meet")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;


    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PostMapping("/save")
    public ResponseMessage<MeetingResponse> saveMeeting (HttpServletRequest httpServletRequest,
                                                         @Valid @RequestBody MeetingRequest meetingRequest){

        return meetingService.saveMeeting(httpServletRequest, meetingRequest);

    }

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PutMapping("/update/{meetingId}")
    public ResponseMessage<MeetingResponse> updateMeeting (@RequestBody @Valid MeetingRequest meetingRequest,
                                                           @PathVariable Long meetingId,
                                                           HttpServletRequest httpServletRequest){

        return meetingService.updateMeeting(meetingRequest, meetingId, httpServletRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/getAll")
    public List<MeetingResponse> getAll(){

        return meetingService.getAll();

    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteById(@PathVariable Long id){
        return meetingService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @GetMapping("/getAllByTeacher")
    public ResponseEntity<List<MeetingResponse>> getAllMeetingsByLoggedInTeacher(HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(meetingService.getAllMeetingsByLoggedInTeacher(httpServletRequest));

    }

    @PreAuthorize("hasAnyAuthority('Student')")
    @GetMapping("/getAllByStudent")
    public ResponseEntity<List<MeetingResponse>> getAllMeetingsByLoggedInStudent(HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(meetingService.getAllMeetingsByLoggedInStudent(httpServletRequest));

    }


    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/getAllByPage")
    public Page<MeetingResponse> getAllByPage(@RequestParam(value = "page") int page,
                                              @RequestParam(value = "size") int size){

        return meetingService.getAllByPage(page, size);

    }


    @PreAuthorize("hasAnyAuthority('Teacher')")
    @GetMapping("/getAllByPageTeacher")
    public Page<MeetingResponse> getAllByPageTeacher(HttpServletRequest httpServletRequest,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size){

        return meetingService.getAllByPageTeacher(httpServletRequest, page, size);

    }

}

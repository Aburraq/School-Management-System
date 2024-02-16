package com.burak.backendproject.initialwork.service.business;

import com.burak.backendproject.initialwork.entity.concretes.business.Meet;
import com.burak.backendproject.initialwork.payload.mappers.MeetingMapper;
import com.burak.backendproject.initialwork.payload.messages.ErrorMessages;
import com.burak.backendproject.initialwork.payload.request.business.MeetingRequest;
import com.burak.backendproject.initialwork.payload.response.business.MeetingResponse;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.repository.business.MeetingRepository;
import com.burak.backendproject.initialwork.service.helper.MeetingHelper;
import com.burak.backendproject.initialwork.service.helper.MethodHelper;
import com.burak.backendproject.initialwork.service.user.UserService;
import com.burak.backendproject.initialwork.service.validator.DateTimeValidator;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.exception.ResourceNotFoundException;
import com.burak.backendproject.initialwork.payload.messages.SuccessMessages;
import com.burak.backendproject.initialwork.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final UserService userService;

    private final MethodHelper methodHelper;

    private final DateTimeValidator dateTimeValidator;

    private final MeetingMapper meetingMapper;

    private final MeetingHelper meetingHelper;

    private final PageableHelper pageableHelper;

    public ResponseMessage<MeetingResponse> saveMeeting(HttpServletRequest httpServletRequest,
                                                        MeetingRequest meetingRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);
        dateTimeValidator.checkTimeWithException(meetingRequest.getStartTime(), meetingRequest.getStopTime());

        meetingHelper.checkMeetingConflicts(meetingRequest.getStudentIds(),
                teacher.getId(),
                meetingRequest.getDate(),
                meetingRequest.getStartTime(),
                meetingRequest.getStopTime());

        List<User> students = methodHelper.getUserList(meetingRequest.getStudentIds());

        Meet meet = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
        meet.setStudentList(students);
        meet.setAdvisoryTeacher(teacher);

        Meet savedMeet = meetingRepository.save(meet);

        return ResponseMessage.<MeetingResponse>builder()
                .message(SuccessMessages.MEET_SAVE)
                .returnBody(meetingMapper.mapMeetToMeetingResponse(savedMeet))
                .httpStatus(HttpStatus.OK)
                .build();


    }




    public ResponseMessage<MeetingResponse> updateMeeting(MeetingRequest meetingRequest,
                                                          Long meetingId,
                                                          HttpServletRequest httpServletRequest) {
        Meet meet = meetingExistById(meetingId);
        meetingHelper.isMeetingMatchedWithTeacher(meet, httpServletRequest);
        dateTimeValidator.checkTimeWithException(meetingRequest.getStartTime(), meetingRequest.getStopTime());
        meetingHelper.checkMeetingConflicts(meetingRequest.getStudentIds(), meet.getAdvisoryTeacher().getId(),
                meetingRequest.getDate(), meetingRequest.getStartTime(), meetingRequest.getStopTime());

        List<User> students = methodHelper.getUserList(meetingRequest.getStudentIds());

        Meet meetToUpdate = meetingMapper.mapMeetingRequestToMeet(meetingRequest);

        meetToUpdate.setStudentList(students);
        meetToUpdate.setAdvisoryTeacher(meet.getAdvisoryTeacher());
        meetToUpdate.setId(meetingId);

        Meet updatedMeet = meetingRepository.save(meetToUpdate);

        return ResponseMessage.<MeetingResponse>builder()
                .message(SuccessMessages.MEET_UPDATE)
                .httpStatus(HttpStatus.OK)
                .returnBody(meetingMapper.mapMeetToMeetingResponse(updatedMeet))
                .build();

    }

    public Meet meetingExistById(Long id){

        return meetingRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessages.MEET_NOT_FOUND_MESSAGE, id))
        );

    }


    public List<MeetingResponse> getAll() {

        return meetingRepository.findAll().stream()
                .map(meetingMapper::mapMeetToMeetingResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteById(Long id) {

        meetingExistById(id);
        meetingRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.MEET_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<MeetingResponse> getAllMeetingsByLoggedInTeacher(HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);

        return meetingRepository.getByAdvisoryTeacher_IdEquals(teacher.getId())
                .stream().map(meetingMapper::mapMeetToMeetingResponse)
                .collect(Collectors.toList());

    }

    public List<MeetingResponse> getAllMeetingsByLoggedInStudent(HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User student = methodHelper.loadUserByName(username);

        return meetingRepository.findByStudentList_IdEquals(student.getId())
                .stream().map(meetingMapper::mapMeetToMeetingResponse)
                .collect(Collectors.toList());

    }

    public Page<MeetingResponse> getAllByPage(int page, int size) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        return meetingRepository.findAll(pageable).map(meetingMapper::mapMeetToMeetingResponse);

    }


    public Page<MeetingResponse> getAllByPageTeacher(HttpServletRequest httpServletRequest, int page, int size) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        return meetingRepository.findByAdvisoryTeacher_IdEquals(teacher.getId(), pageable)
                .map(meetingMapper::mapMeetToMeetingResponse);

    }
}

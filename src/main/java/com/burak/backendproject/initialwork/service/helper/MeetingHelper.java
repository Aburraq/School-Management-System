package com.burak.backendproject.initialwork.service.helper;

import com.burak.backendproject.initialwork.entity.concretes.business.Meet;
import com.burak.backendproject.initialwork.exception.ConflictException;
import com.burak.backendproject.initialwork.payload.messages.ErrorMessages;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.entity.enums.RoleType;
import com.burak.backendproject.initialwork.exception.BadRequestException;
import com.burak.backendproject.initialwork.repository.business.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MeetingHelper {

    private final MethodHelper methodHelper;

    private final MeetingRepository meetingRepository;


    public void isMeetingMatchedWithTeacher(Meet meet, HttpServletRequest httpServletRequest){

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);

        if (meet.getAdvisoryTeacher().getId().equals(teacher.getId())){

            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

        }

    }

    public void checkMeetingConflicts(List<Long> studentIdList, Long teacherId, LocalDate meetingDate,
                                       LocalTime startTime, LocalTime stopTime){

        List<Meet> existingMeetings = new ArrayList<>();

        for (Long id:studentIdList){
            ;
            methodHelper.checkRole(methodHelper.isUserExist(id), RoleType.STUDENT);
            existingMeetings.addAll(meetingRepository.findByStudentList_IdEquals(id));

        }

        existingMeetings.addAll(meetingRepository.getByAdvisoryTeacher_IdEquals(teacherId));

        for (Meet meet:existingMeetings){
            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if(meet.getDate().equals(meetingDate) && (
                    (startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                            (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) ||
                            (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) ||
                            (startTime.equals(existingStartTime) || stopTime.equals(existingStopTime))
            )) {
                throw new ConflictException(ErrorMessages.MEET_HOURS_CONFLICT);
            }
        }

    }
}

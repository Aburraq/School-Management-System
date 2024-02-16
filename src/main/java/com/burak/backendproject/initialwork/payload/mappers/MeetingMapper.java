package com.burak.backendproject.initialwork.payload.mappers;

import com.burak.backendproject.initialwork.entity.concretes.business.Meet;
import com.burak.backendproject.initialwork.payload.request.business.MeetingRequest;
import com.burak.backendproject.initialwork.payload.response.business.MeetingResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MeetingMapper {

    public Meet mapMeetingRequestToMeet(MeetingRequest meetingRequest){

        return Meet.builder()
                .date(meetingRequest.getDate())
                .startTime(meetingRequest.getStartTime())
                .stopTime(meetingRequest.getStopTime())
                .description(meetingRequest.getDescription())
                .build();

    }

    public MeetingResponse mapMeetToMeetingResponse(Meet meet){

        return MeetingResponse.builder()
                .id(meet.getId())
                .date(meet.getDate())
                .startTime(meet.getStartTime())
                .stopTime(meet.getStopTime())
                .description(meet.getDescription())
                .advisorTeacherId(meet.getAdvisoryTeacher().getId())
                .teacherSsn(meet.getAdvisoryTeacher().getSsn())
                .teacherName(meet.getAdvisoryTeacher().getName())
                .students(meet.getStudentList())
                .build();

    }



}

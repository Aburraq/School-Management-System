package com.burak.backendproject.initialwork.controller.business;

import com.burak.backendproject.initialwork.payload.request.business.EducationTermRequest;
import com.burak.backendproject.initialwork.payload.response.business.EducationTermResponse;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;

    @PreAuthorize("hasAnyAuthority('Admin','Dean')")
    @PostMapping("/save")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(@Valid @RequestBody EducationTermRequest educationTermRequest){

        return educationTermService.saveEducationTerm(educationTermRequest);

    }

    // TODO implement this method.
    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @GetMapping("/getAll")
    public List<EducationTermResponse> getAllEducationTerms(){

        return null;

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @GetMapping("{id}")
    public EducationTermResponse getEducationTermById(@PathVariable Long id){

        return educationTermService.findById(id);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @GetMapping("/getAllEducationTermsByPage")
    public Page<EducationTermResponse> getAllEducationTermsByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){

        return educationTermService.getAllByPage(page,size,sort,type);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteEducationTermById(@PathVariable Long id){

        return educationTermService.deleteById(id);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean')")
    @PutMapping("/update/{id}")
    public ResponseMessage<EducationTermResponse> updateEducationTerm(
            @PathVariable Long id,
            @Valid @RequestBody EducationTermRequest educationTermRequest
    ){

        return educationTermService.updateEducationTerm(id, educationTermRequest);

    }


}

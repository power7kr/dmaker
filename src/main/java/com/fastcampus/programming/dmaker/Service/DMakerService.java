package com.fastcampus.programming.dmaker.Service;

import com.fastcampus.programming.dmaker.Code.StatusCode;
import com.fastcampus.programming.dmaker.DTO.CreateDeveloper;
import com.fastcampus.programming.dmaker.DTO.DeveloperDTO;
import com.fastcampus.programming.dmaker.DTO.DeveloperDetailDTO;
import com.fastcampus.programming.dmaker.DTO.EditDeveloper;
import com.fastcampus.programming.dmaker.Entity.Developer;
import com.fastcampus.programming.dmaker.Entity.RetiredDeveloper;
import com.fastcampus.programming.dmaker.Exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.Exception.DMakerException;
import com.fastcampus.programming.dmaker.Repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.Repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.Type.DeveloperLevel;
import com.fastcampus.programming.dmaker.Type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static com.fastcampus.programming.dmaker.Exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request req){

        validateCreateDeveloperRequest(req);

        Developer developer = Developer.builder()
                .developerLevel(req.getDeveloperLevel())
                .developerSkillType(req.getDeveloperSkillType())
                .experienceYears(req.getExperienceYears())
                .memberID(req.getMemberID())
                .name(req.getName())
                .age(req.getAge())
                .statusCode(StatusCode.EMPLOYED)
                .build();

        //DB에 저장하는 영속화
        developerRepository.save(developer);
        //Request에서 Response를 즉시 만드는 함수
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request req) {
        //business validation
        validateDeveloperLevel(req.getDeveloperLevel(), req.getExperienceYears());

        developerRepository.findByMemberID(req.getMemberID())
                //Validation을 1줄로 처리하기
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }

    public List<DeveloperDTO> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDTO getDeveloperDetail(String memberID) {
        return developerRepository.findByMemberID(memberID)
                //Optional했던 Developer Entity를 DeveloperDetailDTO로 변경한다
                //하지만 아직 Optaional한 데이터이다.
                .map(DeveloperDetailDTO::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDTO editDeveloper(String memberID, EditDeveloper.Request req) {
        validateEditDeveloperRequest(req,memberID);

        var developer = developerRepository.findByMemberID(memberID)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));

        developer.setDeveloperLevel(req.getDeveloperLevel());
        developer.setDeveloperSkillType(req.getDeveloperSkillType());
        developer.setExperienceYears(req.getExperienceYears());

        return DeveloperDetailDTO.fromEntity(developer);
    }

    private void validateEditDeveloperRequest(
            EditDeveloper.Request req,
            String memberID
    ){
        validateDeveloperLevel(
                req.getDeveloperLevel(),
                req.getExperienceYears()
        );
    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears){
        if(developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNIOR
                && experienceYears > 4){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    @Transactional
    public DeveloperDetailDTO deleteDeveloper(String memberID) {
        // 1. EMPLOYED -> RETIRED
        var developer = developerRepository.findByMemberID(memberID)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        // 2. Save into RetiredDeveloper
        var retired = RetiredDeveloper.builder()
                .memberID(memberID)
                .name(developer.getName())
                .build();
        retiredDeveloperRepository.save(retired);
        return DeveloperDetailDTO.fromEntity(developer);
    }
}

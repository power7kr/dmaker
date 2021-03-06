package com.fastcampus.programming.dmaker.Service;

import com.fastcampus.programming.dmaker.Code.StatusCode;
import com.fastcampus.programming.dmaker.DTO.CreateDeveloper;
import com.fastcampus.programming.dmaker.DTO.DeveloperDetailDTO;
import com.fastcampus.programming.dmaker.Entity.Developer;
import com.fastcampus.programming.dmaker.Exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.Exception.DMakerException;
import com.fastcampus.programming.dmaker.Repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.Repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.Type.DeveloperLevel;
import com.fastcampus.programming.dmaker.Type.DeveloperSkillType;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fastcampus.programming.dmaker.Code.StatusCode.EMPLOYED;
import static com.fastcampus.programming.dmaker.Exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcampus.programming.dmaker.Type.DeveloperLevel.SENIOR;
import static com.fastcampus.programming.dmaker.Type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {
    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    private final Developer defaultDeveloper = Developer.builder()
                        .developerLevel(SENIOR)
                        .developerSkillType(FRONT_END)
                        .experienceYears(12)
                        .statusCode(EMPLOYED)
                        .name("name")
                        .age(12)
                        .build();

    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
                .developerSkillType(FRONT_END)
                .experienceYears(12)
                .memberID("memberID")
                .name("name")
                .age(32)
                .build();

    @Test
    public void testSomething(){
        //given
        given(developerRepository.findByMemberID(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        //when
        DeveloperDetailDTO developerDetail = dMakerService.getDeveloperDetail("helloworld");

        //then
        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }

    @Test
    void CreateDeveloperTest_success(){
        //given : Mocking, Test??? ????????? ???????????? ?????? ????????? ??????


        //findByMemberID ????????? ?????? ?????? Mocking ??????.
        given(developerRepository.findByMemberID(anyString()))
                .willReturn(Optional.empty());
        //??? ????????? ?????? ???????????? ??? ?????? (?????? ??????????????? ?????????)
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when : ?????????????????? ?????? ?????? ??? ??? ?????????

        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultCreateRequest);

        //then : ?????????. ???????????? ????????? ??????(assertion)
        verify(developerRepository, times(1))
                //?????? developerRepository?????? ??????????????? ????????? ??? ??????
                .save(captor.capture());
        Developer savedDeveloper = captor.getValue();
        assertEquals(SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(12, savedDeveloper.getExperienceYears());
    }

    @Test
    void CreateDeveloperTest_failed_with_duplicated(){
        //given : Mocking, Test??? ????????? ???????????? ?????? ????????? ??????

        //findByMemberID ????????? ?????? ?????? Mocking ??????.
        given(developerRepository.findByMemberID(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        //when : ?????????????????? ?????? ?????? ??? ??? ?????????

        //then : ?????????. ???????????? ????????? ??????(assertion) -> ???????????? ????????? ??????
        DMakerException dMakerException = assertThrows(
                DMakerException.class,
                () -> dMakerService.createDeveloper(defaultCreateRequest));

        //?????? ?????? ???????????? ?????????
        assertEquals(DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }

}
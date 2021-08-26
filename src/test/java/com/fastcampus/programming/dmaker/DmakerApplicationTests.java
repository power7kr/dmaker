package com.fastcampus.programming.dmaker;

import com.fastcampus.programming.dmaker.Code.StatusCode;
import com.fastcampus.programming.dmaker.DTO.CreateDeveloper;
import com.fastcampus.programming.dmaker.DTO.DeveloperDTO;
import com.fastcampus.programming.dmaker.DTO.DeveloperDetailDTO;
import com.fastcampus.programming.dmaker.Entity.Developer;
import com.fastcampus.programming.dmaker.Repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.Repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.Service.DMakerService;
import com.fastcampus.programming.dmaker.Type.DeveloperLevel;
import com.fastcampus.programming.dmaker.Type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DmakerApplicationTests {

	@Mock
	private DeveloperRepository developerRepository;
	private RetiredDeveloperRepository retiredDeveloperRepository;

	@InjectMocks
	private DMakerService dMakerService;

	@Test
	public void contextLoads() {
		//Put Anything to MemberID : will [willReturn] Response received
		given(developerRepository.findByMemberID(anyString()))
				.willReturn(Optional.of(Developer.builder()
						.developerLevel(DeveloperLevel.SENIOR)
						.developerSkillType(DeveloperSkillType.FRONT_END)
						.experienceYears(12)
						.statusCode(StatusCode.EMPLOYED)
						.name("name")
						.age(12)
						.build()));

		DeveloperDetailDTO developerDetail = dMakerService.getDeveloperDetail("memberID");

		assertEquals(DeveloperLevel.SENIOR, developerDetail.getDeveloperLevel());
		assertEquals(DeveloperSkillType.FRONT_END, developerDetail.getDeveloperSkillType());
		assertEquals(12, developerDetail.getExperienceYears());
	}

}

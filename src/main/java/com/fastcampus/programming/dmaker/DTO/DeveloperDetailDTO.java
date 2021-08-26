package com.fastcampus.programming.dmaker.DTO;

import com.fastcampus.programming.dmaker.Code.StatusCode;
import com.fastcampus.programming.dmaker.Entity.Developer;
import com.fastcampus.programming.dmaker.Type.DeveloperLevel;
import com.fastcampus.programming.dmaker.Type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetailDTO {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;

    private Integer experienceYears;
    private String memberID;
    private StatusCode statusCode;
    private String name;
    private Integer age;

    public static DeveloperDetailDTO fromEntity(Developer developer) {
        return DeveloperDetailDTO.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .experienceYears(developer.getExperienceYears())
                .memberID(developer.getMemberID())
                .statusCode(developer.getStatusCode())
                .name(developer.getName())
                .age(developer.getAge())
                .build();
    }
}

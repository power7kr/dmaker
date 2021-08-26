package com.fastcampus.programming.dmaker.DTO;

import com.fastcampus.programming.dmaker.Entity.Developer;
import com.fastcampus.programming.dmaker.Type.DeveloperLevel;
import com.fastcampus.programming.dmaker.Type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeveloperDTO {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberID;

    public static DeveloperDTO fromEntity(Developer developer){
        return DeveloperDTO.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberID(developer.getMemberID())
                .build();
    }
}

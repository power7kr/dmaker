package com.fastcampus.programming.dmaker.DTO;

import com.fastcampus.programming.dmaker.Entity.Developer;
import com.fastcampus.programming.dmaker.Type.DeveloperLevel;
import com.fastcampus.programming.dmaker.Type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateDeveloper {
    
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request{
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;
        
        @NotNull
        @Size(min = 3, max = 50, message = "memberID size : 3 ~ 50")
        private String memberID;
        @NotNull
        @Size(min = 3, max = 20, message = "name size must 3 ~ 20")
        private String name;

        @Min(18)
        private Integer age;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;

        private String memberID;
        private String name;
        private Integer age;

        public static Response fromEntity(Developer developer){
            return Response.builder()
                    .developerLevel(developer.getDeveloperLevel())
                    .developerSkillType(developer.getDeveloperSkillType())
                    .experienceYears(developer.getExperienceYears())
                    .memberID(developer.getMemberID())
                    .build();
        }
    }
}

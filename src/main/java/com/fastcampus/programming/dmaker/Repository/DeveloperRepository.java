package com.fastcampus.programming.dmaker.Repository;

import com.fastcampus.programming.dmaker.Code.StatusCode;
import com.fastcampus.programming.dmaker.Entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository
        extends JpaRepository<Developer, Long> {

    //내용 없이 함수명만으로 검색이 가능한 JPA의 기능
    Optional<Developer> findByMemberID(String memberID);
    List<Developer> findDevelopersByStatusCodeEquals(StatusCode statusCode);
}

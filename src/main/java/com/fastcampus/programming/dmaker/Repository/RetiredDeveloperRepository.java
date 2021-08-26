package com.fastcampus.programming.dmaker.Repository;

import com.fastcampus.programming.dmaker.Entity.Developer;
import com.fastcampus.programming.dmaker.Entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDeveloperRepository
        extends JpaRepository<RetiredDeveloper, Long> {
}

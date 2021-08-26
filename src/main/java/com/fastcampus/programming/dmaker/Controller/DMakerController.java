package com.fastcampus.programming.dmaker.Controller;

import com.fastcampus.programming.dmaker.DTO.CreateDeveloper;
import com.fastcampus.programming.dmaker.DTO.DeveloperDTO;
import com.fastcampus.programming.dmaker.DTO.DeveloperDetailDTO;
import com.fastcampus.programming.dmaker.DTO.EditDeveloper;
import com.fastcampus.programming.dmaker.Service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//사용자의 입력이 최초로 받아들여지는 위치
@RestController
@RequiredArgsConstructor
@Slf4j
public class DMakerController {

    //Service Bean 주입
    private final DMakerService dMakerService;

    // GET /developers HTTP/1.1
    // -> localhost:8080/developers
    @GetMapping("/developers")
    public List<DeveloperDTO> getAllEmployedDevelopers() {
        log.info("RECEIVED>> GET /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    //{memberID} : Path Variable
    @GetMapping("/developers/{memberID}")
    public DeveloperDetailDTO getDeveloperDetail(
            @PathVariable String memberID
    ) {
        log.info("RECEIVED>> GET /developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberID);
    }

    @PutMapping("/developer/{memberID}")
    public DeveloperDetailDTO editDeveloper(
            @PathVariable String memberID,
            @Valid @RequestBody EditDeveloper.Request req
    ){
        log.info("RECEIVED>> PUT /developers/{" + memberID + "} HTTP/1.1");

        return dMakerService.editDeveloper(memberID,req);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request req
            ) {

        log.info("RECEIVED>> REQ : {}", req);

        return dMakerService.createDeveloper(req);
    }

    @DeleteMapping("/developer/{memberID}")
    public DeveloperDetailDTO deleteDeveloper(
            @PathVariable String memberID
    ){
        return dMakerService.deleteDeveloper(memberID);
    }
}

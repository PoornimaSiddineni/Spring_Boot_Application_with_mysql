package com.example.sql2.controller;

import com.example.sql2.entity.Substring;
import com.example.sql2.repository.LcsRepository;
import com.example.sql2.service.LcsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin("http://localhost:8080")
@RestController

public class LcsController {

    private final LcsService lcsSer;
    private final LcsRepository lcsRepo;


    public LcsController(LcsService LcsService1,LcsRepository lcsRepository)
    {
        this.lcsSer = LcsService1;
        this.lcsRepo = lcsRepository;
    }

    @GetMapping( "/lcs")
    public ResponseEntity<Optional<List<Substring>>> getAll() {
        // return this.lcsSer.getAll();
        Optional<List<Substring>> list1 = this.lcsSer.getAll();
        return ok(list1);

    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<Substring> getListById(@PathVariable String id) {

        Optional<Substring> mylist1 = this.lcsSer.getById(id);
        if(mylist1.isPresent()) {
            return ok(mylist1.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(value="/lcs")
    public Substring create(@RequestBody List<String> stringList)
    {
        return this.lcsSer.to_Create(stringList);
    }

}


package com.example.sql2.repository;

import com.example.sql2.entity.Substring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LcsRepository extends JpaRepository<Substring,String> {

}

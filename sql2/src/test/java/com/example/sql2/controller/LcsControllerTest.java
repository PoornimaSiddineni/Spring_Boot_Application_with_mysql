package com.example.sql2.controller;

import com.example.sql2.entity.Substring;
import com.example.sql2.service.LcsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableAutoConfiguration
@SpringBootTest
public class LcsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LcsService lcsService;

    Substring Record_1 = new Substring((long) 1,Arrays.asList("abc","defabc","jgdabc"),1,Arrays.asList("abc"));
    Substring Record_2 = new Substring(10L,Arrays.asList("123abc","defa123bc","jgd123abc"),1,Arrays.asList("abc"));
    Substring Record_3 = new Substring(14L,Arrays.asList("xyz","defabcxyz","jgdabcxyz"),1,Arrays.asList("abc"));


    @Test
    public void getSubstringByIdTest() throws Exception{


        Substring sub = new Substring(12L, Arrays.asList("abc","defabc","cvhabc"),1, Arrays.asList("abc"));

        //mocking
        when(lcsService.getById(any())).thenReturn(java.util.Optional.of(sub));

        //create the mock HTTP request to verify the expected result

        mockMvc.perform(MockMvcRequestBuilders.get("/lists/{id}",12).contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.request").value(Matchers.containsInAnyOrder("abc", "defabc", "cvhabc")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(Matchers.containsInAnyOrder("abc")))
                .andExpect(status().isOk());
    }

    @Test
    public void getSubstringByIdNotTest() throws Exception{

        //mocking
        when(lcsService.getById(any())).thenReturn(Optional.empty());

        //create the mock HTTP request to verify the expected result

        mockMvc.perform(MockMvcRequestBuilders.get("/lists/{id}",12).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTest() throws Exception{

        Substring str = new Substring(2L, Arrays.asList("abc","defabc","jgdabc"),1,Arrays.asList("abc"));

        when(lcsService.to_Create(any())).thenReturn(str);

        mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                        .content(new ObjectMapper().writeValueAsString(Arrays.asList("abc","defabc","jgdabc")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void getAllTest() throws Exception{

        List<Substring> mylist = new ArrayList<>(Arrays.asList(Record_1, Record_2, Record_3));
        when(lcsService.getAll()).thenReturn((java.util.Optional.of(mylist)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/lcs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)));

    }
}


package com.example.sql2.service;

import com.example.sql2.entity.Substring;
import com.example.sql2.repository.LcsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class LcsServiceTest {
    @Mock
    @Autowired
    private LcsRepository lcsRepository;
    @Autowired
    @InjectMocks
    private LcsService lcsService;

    private Substring substring;

    private Substring substrs1;
    private Substring substrs2;
    List<Substring> stringsList;

    @BeforeEach
    public void setUp() {
        stringsList = new ArrayList<>();
        substrs1 = new Substring(1L, Arrays.asList("abc","defabc","jgdabc"),1,Arrays.asList("abc"));
        substrs2 = new Substring(10L,Arrays.asList("123abc","defa123bc","jgd123abc"),1,Arrays.asList("abc"));
        stringsList.add(substrs1);
        stringsList.add(substrs2);
    }

    @AfterEach
    public void tearDown() {
        substrs1 = substrs2 = null;
        stringsList = null;
    }


    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyList_whenGetAll_thenReturnEmptyList(){

        given(lcsRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        Optional<List<Substring>> strList = lcsService.getAll();

        // then - verify the output
        //assertThat(stringsList).isNotEqualTo(strList);
        assertThat(strList.get().size()).isEqualTo(0);

    }


    // JUnit test for getAll method
    @DisplayName("JUnit test for getAll method")
    @Test
    public void givenList_whenGetAll_thenReturnList(){
        // given - precondition or setup

        given(lcsRepository.findAll()).willReturn(stringsList);

        // when -  action or the behaviour that we are going test
        Optional<List<Substring>> subList = lcsService.getAll();

        // then - verify the output
        assertThat(subList).isNotNull();
        assertThat(subList.get().size()).isEqualTo(2);
    }

    // JUnit test for getById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenId_whenGetById_thenReturnObject(){
        // given
        given(lcsRepository.findById("1")).willReturn(Optional.ofNullable(substrs1));

        // when
        Substring saved = lcsService.getById(String.valueOf(substrs1.getId())).get();

        // then
        assertThat(saved).isNotNull();

    }

    // JUnit test for save method
    @DisplayName("JUnit test for save method")
    @Test
    public void givenObject_whenSave_thenReturnObject(){

        List<String> mylist = Arrays.asList("abc","defabc","jgdabc");

        System.out.println(lcsRepository);
        System.out.println(lcsService);

        // when -  action or the behaviour that we are going test
        Substring saved = lcsService.to_Create( mylist);

        System.out.println(saved.getResponse());
        // then - verify the output
        assertThat(saved).isNotNull();
    }

}

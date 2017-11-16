package com.example.Glasses.web.controllers;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.repositories.RoleRepository;
import com.example.Glasses.persistence.repositories.UserRepository;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.basic;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    RequestSpecBuilder build;
    RequestSpecification rspec;
    //private String URL_PREFIX = "http://localhost:8080/test/auth/users";//spring-rest-query-language/auth/api/myusers";
    private String URL_PREFIX2 = "test/users";
    private User Bozena;
    private User Piotr;

    @Before
    public void setUp() throws Exception {
        //RestAssuredMockMvc.standaloneSetup(new UserController(userRepository,roleRepository));
        RestAssured.authentication = basic("username", "password");
        //RestAssured.given().auth()

        Bozena = User.builder()
                .userId(1)
                .email("bozena@gmail.com")
                .enabled(true)
                .password("admin")
                .build();
        userRepository.save(Bozena);

        Piotr = User.builder()
                .userId(2)
                .email("piotr@gmail.com")
                .enabled(true)
                .password("qwerty")
                .build();
        userRepository.save(Piotr);
    }

//    @Test
//    public void whenGettingListOfUsers_thenCorrect() {
//        //final Response response = RestAssured.given().get(URL_PREFIX);
//        //final User[] result = response.as(User[].class);
//        //assertEquals(result.length, 2);
////        MockMvcResponse res =
////                given()
////                .get("test/auth/users");
////        Assert.assertEquals (res.statusCode (), 200);
//      //  given()
//    }

    @Test
    public void whenGettingListOfUsers_thenCorrect() {
        given()
                //.contentType("application/json")
                //.standaloneSetup(new UserController(userRepository,roleRepository))
        .when()
                .get("/page")
        .then()
                .assertThat()
                //.body("email", equalTo("bozena@gmail.com"));
                //.body(equalTo("bozena@gmail.com"))
                .statusCode(200);

    }


    private RequestSpecification givenAuth() {

        return RestAssured.given().auth().preemptive().basic("admin", "admin");
    }

}
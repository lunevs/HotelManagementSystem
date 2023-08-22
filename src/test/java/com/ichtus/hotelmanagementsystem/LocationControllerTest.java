package com.ichtus.hotelmanagementsystem;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import static io.restassured.RestAssured.given;

public class LocationControllerTest {

    @WithMockUser(authorities = "ADMIN")
    @Test
    void endpointWhenUserAuthorityThenAuthorized() {
//        this.mvc.perform(get("/endpoint"))
//                .andExpect(status().isOk());
    }

//    @WithMockUser
//    @Test
//    void endpointWhenNotUserAuthorityThenForbidden() {
//        this.mvc.perform(get("/endpoint"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    void anyWhenUnauthenticatedThenUnauthorized() {
//        this.mvc.perform(get("/any"))
//                .andExpect(status().isUnauthorized())
//    }


//    @BeforeEach
//    void setUp() {
//        RestAssured.port = 8080;
//        RestAssured.baseURI = "http://localhost";
//    }
//
//    @Test
//    void adminShouldAuth() {
//        FormAuthConfig cfg = new FormAuthConfig("/login", "username", "password").withLoggingEnabled();
//
//        String responseStr = given()
//                .contentType(ContentType.URLENC)
//                .auth().form("admin", "admin123", cfg)
//                .when().get("/locations")
//                .then()
//                .assertThat().statusCode(HttpStatus.OK.value())
//                .extract().body().asString();
//
//        //System.out.println(responseStr);
//
//    }
}

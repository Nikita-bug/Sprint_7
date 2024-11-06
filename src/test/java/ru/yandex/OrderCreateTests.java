package ru.yandex;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.static_data.StaticData;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTests extends StaticData {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;


    public OrderCreateTests(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_PAGE;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> order() {
        return Arrays.asList(new Object[][]{

                {"Naruto", "Uzumaki", "Konoha, village", 1, "+75555555555", 1, "2024-05-05", "any comment", List.of("BLACK", "GREY")},
                {"Sasuke", "Uchiha", "Konoha", 2, "+76666666666", 2, "2024-06-06", "comment here", List.of("GREY")},
                {"Madara", "Uchiha", "Konoha", 3, "+78888888888", 3, "2024-07-07", "comment", List.of("BLACK")}
        });
    }


    @Test
    @Step("Проверка создания заказа")
    public void ordersRequest() {


        given().log().all()
                .contentType(ContentType.JSON)
                .auth().oauth2(TOKEN)
                .and()
                .body(this)
                .when()
                .post("/api/v1/orders")
                .then().log().all()
                .assertThat().statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body(notNullValue());
    }


}

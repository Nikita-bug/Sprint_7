package ru.yandex;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.client.CourierClient;
import ru.yandex.models.Courier;
import ru.yandex.models.CourierId;
import ru.yandex.models.CourierRespMessage;
import ru.yandex.static_data.StaticData;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.generator.CourierRandomizer.randomCourier;
import static ru.yandex.generator.CourierRandomizer.randomCourierWithoutLogin;


public class CourierLoginTests extends StaticData {

    private CourierClient courierClient;
    private int id;
    private String message;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_PAGE;
    }

    @Test
    @Step("Проверка авторизации курьера")
    public void loginCourier() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);
        Response loginResponse = courierClient.login(courier);
        id = loginResponse.as(CourierId.class).getId();

        response
                .then().log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("ok", equalTo(true));

        loginResponse
                .then().log().all()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("id", equalTo(id));

    }

    @Test
    @Step("Проверка авторизации курьера без логина")
    public void loginCourierWithoutLogin() {

        Courier courier = randomCourierWithoutLogin();
        courierClient = new CourierClient();

        Response loginResponse = courierClient.login(courier);
        message = loginResponse.as(CourierRespMessage.class).getMessage();

        loginResponse
                .then().log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo(message));
    }

    @Test
    @Step("Проверка авторизации курьера c неправильным логином")
    public void loginCourierWithWrongCreds() {

        courierClient = new CourierClient();

        Response loginResponse = courierClient.login(NOT_EXISTING_COURIER);
        message = loginResponse.as(CourierRespMessage.class).getMessage();

        loginResponse
                .then().log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat()
                .body("message", equalTo(message));
    }


    @After
    public void tearDown() {
        courierClient.delete(id);
    }


}


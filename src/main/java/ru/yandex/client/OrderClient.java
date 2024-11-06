package ru.yandex.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Получение данных о заказах")
    public Response request() {
        return given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .then()
                .statusCode(200).extract().response();

    }


}

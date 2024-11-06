package ru.yandex.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.models.Courier;
import ru.yandex.models.CourierCreds;

import static io.restassured.RestAssured.given;
import static ru.yandex.models.CourierCreds.credsFromCourier;

public class CourierClient {

    @Step("Создание курьера")
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }


    @Step("Авторизация курьра")
    public Response login(Courier courier) {
        CourierCreds creds = credsFromCourier(courier);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("api/v1/courier/" + id);
    }


}

package ru.yandex;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.client.OrderClient;
import ru.yandex.static_data.StaticData;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.*;

public class OrdersResponseTests extends StaticData {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_PAGE;
    }

    @Test
    @Step("Проверка запроса списка заказов")
    public void ordersRequest() {


        orderClient = new OrderClient();

        Response response = orderClient.request();

        response
                .then().log().all()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(notNullValue());
    }



}

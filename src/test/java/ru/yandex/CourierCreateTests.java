package ru.yandex;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.client.CourierClient;
import ru.yandex.models.Courier;
import ru.yandex.models.CourierId;
import ru.yandex.static_data.StaticData;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.generator.CourierRandomizer.*;

public class CourierCreateTests extends StaticData {

    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_PAGE;
    }

    @Test
    @Step("Проверка создания рандомного курьера")
    public void createCourier(){

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
                .body("ok" , equalTo(true));
    }

    @Test
    @Step("Проверка создания рандомного курьера без пароля")
    public void createCourierWithoutPassword(){

        Courier courier = randomCourierWithoutPassword();
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        response
                .then().log().all()
                .assertThat()
                .body("message" , equalTo("Недостаточно данных для создания учетной записи"));

        Assert.assertEquals("Неверный код", SC_BAD_REQUEST, response.statusCode());
    }

    @Test
    @Step("Проверка создания рандомного курьера без логина")
    public void createCourierWithoutLogin(){

        Courier courier = randomCourierWithoutLogin();
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        response
                .then().log().all()
                .assertThat()
                .body("message" , equalTo("Недостаточно данных для создания учетной записи"));

        Assert.assertEquals("Неверный код", SC_BAD_REQUEST, response.statusCode());
    }

    @Test
    @Step("Проверка создания курьера с уже существующим логином")
    public void createTheSameCourier(){

        Courier courier = EXISTING_COURIER;
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        response
                .then().log().all()
                .assertThat()
                .body("message" , equalTo("Этот логин уже используется. Попробуйте другой."));

        Assert.assertEquals("Неверный код", SC_CONFLICT, response.statusCode());
    }


    @After
    public void tearDown(){
        courierClient.delete(id);
    }

}

package ru.yandex.static_data;

import ru.yandex.models.Courier;

public class StaticData {

    protected static String BASE_PAGE = "https://qa-scooter.praktikum-services.ru";

    protected static Courier EXISTING_COURIER = new Courier()
            .withLogin("Pasht")
            .withPassword("1234")
            .withFirstName("saske");

    protected static Courier NOT_EXISTING_COURIER = new Courier()
            .withLogin("randomStringLoginCourier2")
            .withPassword("1234")
            .withFirstName("saske");

    protected static String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NjlkODg3YmQ1NmMxNDAwM2Q0NzkzNTQiLCJpYXQiOjE3Mjk0NTM4MDMsImV4cCI6MTczMDA1ODYwM30.-liWXqd8Qx5dCaBr5_h6QkePOGPP5hqC2TzrvAdkHKk";
}

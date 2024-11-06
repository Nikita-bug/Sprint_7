package ru.yandex.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.models.Courier;

public class CourierRandomizer {

    public static Courier randomCourier() {
        return new Courier()
                .withLogin(RandomStringUtils.random(8))
                .withPassword(RandomStringUtils.random(10))
                .withFirstName(RandomStringUtils.random(14));
    }

    public static Courier randomCourierWithoutPassword() {
        return new Courier()
                .withLogin(RandomStringUtils.random(8))
                .withFirstName(RandomStringUtils.random(14));
    }

    public static Courier randomCourierWithoutLogin() {
        return new Courier()
                .withPassword(RandomStringUtils.random(10))
                .withFirstName(RandomStringUtils.random(14));
    }


}

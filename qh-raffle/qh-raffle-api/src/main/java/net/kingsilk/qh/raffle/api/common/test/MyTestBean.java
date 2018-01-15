package net.kingsilk.qh.raffle.api.common.test;

import java.util.*;

/**
 * Created by zll on 31/07/2017.
 */
public class MyTestBean {


    private String name;

    private Optional<Integer> age = Optional.empty();

    private Optional<String> street = Optional.empty();
    private Optional<List<String>> hobbies = Optional.empty();

    private Optional<Date> birthday = Optional.empty();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Integer> getAge() {
        return age;
    }

    public void setAge(Optional<Integer> age) {
        this.age = age;
    }

    public Optional<String> getStreet() {
        return street;
    }

    public void setStreet(Optional<String> street) {
        this.street = street;
    }

    public Optional<List<String>> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Optional<List<String>> hobbies) {
        this.hobbies = hobbies;
    }

    public Optional<Date> getBirthday() {
        return birthday;
    }

    public void setBirthday(Optional<Date> birthday) {
        this.birthday = birthday;
    }
}

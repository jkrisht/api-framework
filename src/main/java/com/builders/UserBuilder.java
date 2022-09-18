package com.builders;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.pojo.User;

public class UserBuilder {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    public UserBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder setUserStatus(int userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public User getUser() {
        return new User(id, username, firstName, lastName, email, password, phone, userStatus);
    }

    public static User build() {
        Faker faker = new Faker();
        Name name = faker.name();
        String userName = name.username();

        return new UserBuilder()
                .setId(faker.number().randomNumber(8, true))
                .setUsername(userName)
                .setFirstName(name.firstName())
                .setLastName(name.lastName())
                .setEmail(userName + "@gmail.com")
                .setPassword(faker.internet().password())
                .setPhone(faker.phoneNumber().phoneNumber())
                .setUserStatus(0)
                .getUser();
    }

}

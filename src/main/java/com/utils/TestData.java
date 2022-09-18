package com.utils;

import com.pojo.Pet;
import com.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    private final List<User> users;
    private final List<Pet> pets;

    public TestData() {
        this.users = new ArrayList<>();
        this.pets = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public TestData addUser(User user) {
        this.users.add(user);
        return this;
    }

    public TestData addUsers(List<User> users) {
        this.users.addAll(users);
        return this;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public TestData addPets(List<Pet> pets) {
        this.pets.addAll(pets);
        return this;
    }

    public TestData addPet(Pet pet) {
        this.pets.add(pet);
        return this;
    }
}

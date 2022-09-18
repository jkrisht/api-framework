package com.builders;

import com.enums.PetStatus;
import com.github.javafaker.Animal;
import com.github.javafaker.Faker;
import com.pojo.Pet;

import java.util.Collections;
import java.util.List;

public class PetBuilder {
    private long id;
    private Pet.Category category;
    private String name;
    private List<String> photoUrls;
    private List<Pet.Tag> tags;
    private String status;

    private static Pet.Category getCategory() {
        Faker faker = new Faker();
        Pet.Category category1 = new Pet.Category();
        category1.setId(faker.number().randomDigit());
        category1.setName(Animal.class.getSimpleName());
        return category1;
    }

    public PetBuilder setCategory(Pet.Category category) {
        this.category = category;
        return this;
    }

    private static Pet.Tag getTag() {
        Faker faker = new Faker();
        Pet.Tag tag = new Pet.Tag();
        tag.setId(faker.number().randomDigit());
        tag.setName(faker.lorem().word());
        return tag;
    }

    public static Pet build() {
        Faker faker = new Faker();

        return new PetBuilder()
                .setId(faker.number().randomNumber(8, true))
                .setCategory(getCategory())
                .setName(faker.animal().name())
                .setPhotoUrls(Collections.singletonList(faker.internet().avatar()))
                .setTags(Collections.singletonList(getTag()))
                .setStatus(PetStatus.AVAILABLE.getStatus())
                .getPet();
    }

    public PetBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public PetBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PetBuilder setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public PetBuilder setTags(List<Pet.Tag> tags) {
        this.tags = tags;
        return this;
    }

    public PetBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Pet getPet() {
        return new Pet(id, category, name, photoUrls, tags, status);
    }
}

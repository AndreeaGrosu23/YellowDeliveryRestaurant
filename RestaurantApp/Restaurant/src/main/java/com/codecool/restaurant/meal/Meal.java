package com.codecool.restaurant.meal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="MEAL")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private Double price = 5.00;

    public Meal(String name, String image) {
        this.name = name;
        this.image = image;
    }


    @Override
    public String toString() {
        return "{\"id\" : " + id + "," +
                "\"name\": \"" +name+ "\"," +
                "\"image\": \"" + image + "\"," +
                "\"price\": "+ price + "}";

    }
}

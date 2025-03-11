package com.recipes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    private String id;
    private String name;
    private boolean vegetarian;
    private int servings;
    private List<String> ingredients;
    private String instructions;
}

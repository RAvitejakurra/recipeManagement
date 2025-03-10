package com.recipes.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "recipes")
public class Recipe {

	@Id
	private String id;
	
	    private String name;
	    private boolean vegetarian;
	    private int servings;
	    private List<String> ingredients;
	    private String instructions;
}

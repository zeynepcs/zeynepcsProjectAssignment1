package se.jensenyh.javacourse.saltmerch.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable
{
    // todo: needs fields: int id, String category, String title, String description, String previewImage, and List of ColorVariant colorVariants
    
    // todo: all fields should be public and annotated with @JsonProperty
    
    // todo: needs 3 constructors:
    //  1. empty constructor: this one only initializes colorVariants to new ArrayList<>()
    //  2. constructor with id, category, title, description, and previewImage: this one initializes colorVariants to new ArrayList<>()
    //  3. constructor with id, category, title, description, colorVariants
    @JsonProperty
   public  List<ColorVariant> colorVariants = new ArrayList<>();

    @JsonProperty ("id")
    public int id;

    @JsonProperty ("category")
    public String category;

    @JsonProperty ("title")
    public String title;

    @JsonProperty ("description")
    public String description;

    @JsonProperty ("previewImage")
    public String previewImage;




    public Product(int id, String category, String title, String description, String previewImage) {

        this.id = id;
        this.category = category;
        this.title= title;
        this.description= description;
        this.previewImage= previewImage;



    }

    public Product(int id, String category, String title, String description, List colorVariants) {

        this.id = id;
        this.category = category;
        this.title= title;
        this.description= description;
        this.colorVariants= colorVariants;



    }
    public Product() {

    }
}
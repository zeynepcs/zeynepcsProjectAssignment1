package se.jensenyh.javacourse.saltmerch.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItem
{
    // todo: needs fields: int productId, String title, String color, String size, String previewImage, and int quantity
    
    // todo: all fields should be public and annotated with @JsonProperty
    
    // todo: needs 3 constructors:
    //  1. empty constructor
    //  2. constructor with productId, title, color, size, and previewImage
    //  3. constructor with productId, title, color, size, previewImage, and quantity

    @JsonProperty ("productId")
    public int productId;

    @JsonProperty ("title")
    public String title;

    @JsonProperty ("color")
    public String color;

    @JsonProperty ("size")
    public String size;

    @JsonProperty ("previewImage")
    public String previewImage;

    @JsonProperty ("quantity")
    public int quantity;

    public CartItem(int product_id, String title, String color, String size, String preview_image, int quantity) {

        this.productId = product_id;
        this.title = title;
        this.color = color;
        this.size = size;
        this.previewImage = preview_image;
        this.quantity = quantity;

    }




    public CartItem(int productId, String title, String color, String size, String previewImage)
    {
        this.productId = productId;
        this.title = title;
        this.color = color;
        this.size = size;
        this.previewImage = previewImage;



    }

    public  CartItem()
    {


    }

        }


package se.jensenyh.javacourse.saltmerch.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SizeContainer
{
    // todo: needs fields: String size, int stock

    // todo: all fields should be public and annotated with @JsonProperty

    // todo: needs 2 constructors:
    //  1. empty constructor
    //  2. constructor with size and stock

    @JsonProperty ("size")
    public String size;

    @JsonProperty ("stock")
    public int stock;

    public SizeContainer()
    {

    }

    public SizeContainer(String size, int stock) {

        this.size = size;
        this.stock = stock;
    }




    }
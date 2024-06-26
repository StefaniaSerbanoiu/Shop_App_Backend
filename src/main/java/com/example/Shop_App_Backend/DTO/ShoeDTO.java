package com.example.Shop_App_Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShoeDTO { //
    private Integer shoe_id; // modified name from shoeId
    private String product_name; // modified name from productName
    //private String color;
   // private String season;
    private Integer size;
    private Integer price;
    //private float rating;
}

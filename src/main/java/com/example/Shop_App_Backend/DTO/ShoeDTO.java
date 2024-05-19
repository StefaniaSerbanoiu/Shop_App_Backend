package com.example.Shop_App_Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShoeDTO {
    private Integer shoe_id;
    private String product_name;
    //private String color;
   // private String season;
    private Integer size;
    private Integer price;
    //private float rating;
}

package com.example.Shop_App_Backend.Mappers;

import com.example.Shop_App_Backend.DTO.ShoeDTO;
import com.example.Shop_App_Backend.Domain.Shoe;

public class ShoeMapper {
    public ShoeDTO toShoeDTO(Shoe shoe)
    {
        return new ShoeDTO(shoe.getShoe_id(), shoe.getProduct_name(),
                shoe.getSize(), shoe.getPrice());
    }
}

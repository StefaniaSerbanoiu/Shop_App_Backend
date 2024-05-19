package com.example.Shop_App_Backend.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity // for connectivity with database
@Data // for generated constructors, getters and setters
@Table
public class Shoe { //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoe_id")
    private Integer shoe_id;

    @NotBlank
    @Column(name = "product_name")
    private String product_name;
    /*
    //@Column
    private String color;
    //@Column
    private String season;
    */
    @NotNull(message = "Size must not be null")
    @Min(value = 0, message = "Size must be a positive value")
    @Column(name = "size")
    private Integer size;



    @NotNull(message = "Price must not be null")
    @Min(value = 0, message = "Price must be a positive value")
    @Column(name = "price")
    private Integer price;

    // One product can have multiple associated suggestions on how to style it
    @OneToMany(mappedBy = "shoe", cascade = CascadeType.ALL)
    // If we want the cascade operations to be performed on the Shoe entity
    // (e.g., saving or deleting) and the operations to be cascaded to the associated Suggestion entities as well,
    // we use the CascadeType.ALL setting.
    @JsonManagedReference // This annotation is for the "one" side of the relationship
    private List<Suggestion> suggestions;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private User user;


    /*
    //@Column
    @DecimalMin(value = "0.0", message = "Rating must be greater than or equal to 0")
    @DecimalMax(value = "5.0", message = "Rating must be less than or equal to 5")
    private float rating;
     */

    //@OneToMany(mappedBy = "shoe", cascade = CascadeType.ALL)
    // If we want the cascade operations to be performed on the Shoe entity
    // (e.g., saving or deleting) and the operations to be cascaded to the associated Transaction entities as well,
    // we use the CascadeType.ALL setting.
    //private Set<Transaction> transactions = new HashSet<>();
    // If a List was used instead of a Set, it would allow duplicate associations,
    // potentially causing data integrity issues and unnecessary duplication of data
}

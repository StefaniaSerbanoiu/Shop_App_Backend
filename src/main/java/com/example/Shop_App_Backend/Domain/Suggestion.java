package com.example.Shop_App_Backend.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity // for connectivity with database
@Data // for generated constructors, getters and setters
@Table
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_id")
    private Integer suggestion_id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "advice")
    private String advice;

    // A product can have many suggestions about how it can be styled
    @ManyToOne(optional = true) // a suggestion can exist without being associated
    @JoinColumn(name = "shoe_id") // Name of the foreign key column in Suggestion table
    // Corresponds to the primary key in the Shoe table
    //@JsonIgnore // Ignore the shoe field during serialization
    // Used to resolve circular reference because of the bidirectional relation.
    @JsonBackReference // This annotation is for the "many" side of the relationship
    private Shoe shoe;
    /*
    handling of the 1 to many relationship:
        the shoe specific to the advice can be updated
        a suggestion can be added with or without corresponding products
        in other words, the data is managed from 'Suggestion'
     */
}

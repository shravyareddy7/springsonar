package com.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private int id;

    @NotBlank(message = "Movie name is required")
    private String name;

    @NotBlank(message = "Genre is required")
    private String genre;

    @DecimalMin(value = "0.0", inclusive = false, message = "Ticket price must be greater than 0")
    private double ticketPrice;

    private int theatreId; // Reference to Theatre by ID instead of the full Theatre object
}

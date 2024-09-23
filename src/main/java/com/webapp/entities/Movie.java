package com.webapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private int id;

    @NotBlank(message = "Movie name is required")
    @Column(name = "movie_name")
    private String name;

    @NotBlank(message = "Genre is required")
    @Column(name = "movie_genre")
    private String genre;

    @DecimalMin(value = "0.0", inclusive = false, message = "Ticket price must be greater than 0")
    @Column(name = "ticket_price")
    private double ticketPrice;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    public Movie(){}

    public Movie(String name, String genre, double ticketPrice) {
        this.name = name;
        this.genre = genre;
        this.ticketPrice = ticketPrice;
    }

}

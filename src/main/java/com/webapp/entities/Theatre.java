package com.webapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "theatre",uniqueConstraints = @UniqueConstraint(columnNames = {"name", "location"}))
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theatre_id")
    private int id;

    @NotBlank(message = "Theatre name is required")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Theatre location is required")
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Movie> movies;

    public Theatre(){}

    public Theatre(String name, String location, Set<Movie> movies) {
        this.name = name;
        this.location = location;
        this.movies = movies;
    }

    public Theatre(int theatreId, String testTheatre) {
    }

    public void add(Movie movie){
        if(movies==null)
        {
            movies=new HashSet<>();
        }
        movies.add(movie);
    }
}

package com.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreDTO {

    private int id;

    @NotBlank(message = "Theatre name is required")
    private String name;

    @NotBlank(message = "Theatre location is required")
    private String location;

    private List<MovieDTO> movies; // List of MovieDTO objects instead of Movie entities
}

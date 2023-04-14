package com.example.AnimeWeb.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "data",
        "Page",
        "Media",
        "id",
        "season",
        "coverImage"
})

@JsonRootName(value = "data")

public class Data implements Serializable {
    @JsonProperty("Page")
    public Page page;


}

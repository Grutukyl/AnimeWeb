package com.example.AnimeWeb.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Media {

        @JsonProperty("id")
        public int id;
        @JsonProperty("season")
        public String season;

        @JsonProperty("seasonYear")
        public String seasonYear;

        public String description;
        public List<String> genres;

        public coverImage coverImage;
        public Title title;
        public int averageScore;


}
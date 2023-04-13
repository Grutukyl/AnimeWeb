package lixo.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Media {

        @JsonProperty("id")
        public int id;
        @JsonProperty("season")
        public String season;

        public coverImage coverImage;
        public Title title;



}
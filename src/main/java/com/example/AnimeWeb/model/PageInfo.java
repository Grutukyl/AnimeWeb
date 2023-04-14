package com.example.AnimeWeb.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {


    private int total;
    private int currentPage;
    private int lastPage;
    private boolean hasNextPage;
    private int perPage;

}

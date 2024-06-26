package com.news.site.newsapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceDetailDTO {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
}

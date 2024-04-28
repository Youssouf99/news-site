package com.news.site.newsapi.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.news.site.newsapi.dtos.ArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

//@Component
//@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mappings({
            @Mapping(target = "author", expression = "java(articleNode.has(\"author\") ? articleNode.get(\"author\").asText() : null)"),
            @Mapping(target = "title", expression = "java(articleNode.has(\"title\") ? articleNode.get(\"title\").asText() : null)"),
            @Mapping(target = "description", expression = "java(articleNode.has(\"description\") ? articleNode.get(\"description\").asText() : null)"),
            @Mapping(target = "url", expression = "java(articleNode.has(\"url\") ? articleNode.get(\"url\").asText() : null)"),
            @Mapping(target = "urlToImage", expression = "java(articleNode.has(\"urlToImage\") ? articleNode.get(\"urlToImage\").asText() : null)"),
            @Mapping(target = "publishedAt", expression = "java(articleNode.has(\"publishedAt\") ? articleNode.get(\"publishedAt\").asText() : null)"),
            @Mapping(target = "content", expression = "java(articleNode.has(\"content\") ? articleNode.get(\"content\").asText() : null)")
    })
    ArticleDTO jsonNodeToArticleDto(JsonNode articleNode);

/*    @Mappings({
            @Mapping(target = "id", expression = "java(sourceNode.has(\"id\") ? sourceNode.get(\"id\").asText() : null)"),
            @Mapping(target = "name", expression = "java(sourceNode.has(\"name\") ? sourceNode.get(\"name\").asText() : null)"),
            @Mapping(target = "description", expression = "java(sourceNode.has(\"description\") ? sourceNode.get(\"description\").asText() : null)"),
            @Mapping(target = "url", expression = "java(sourceNode.has(\"url\") ? sourceNode.get(\"url\").asText() : null)"),
            @Mapping(target = "category", expression = "java(sourceNode.has(\"category\") ? sourceNode.get(\"category\").asText() : null)"),
            @Mapping(target = "language", expression = "java(sourceNode.has(\"language\") ? sourceNode.get(\"language\").asText() : null)"),
            @Mapping(target = "country", expression = "java(sourceNode.has(\"country\") ? sourceNode.get(\"country\").asText() : null)")
    })
    SourceDTO jsonNodeToSourceDto(JsonNode sourceNode);*/
}

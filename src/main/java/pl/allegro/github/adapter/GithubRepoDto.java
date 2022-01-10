package pl.allegro.github.adapter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRepoDto(
        String name,
        @JsonProperty("stargazers_count") Integer stars,
        String language,
        @JsonProperty("size") Integer sizeInBytes) {
}

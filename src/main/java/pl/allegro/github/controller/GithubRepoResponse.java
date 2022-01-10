package pl.allegro.github.controller;

import pl.allegro.github.adapter.GithubRepoDto;

public record GithubRepoResponse(String name, Integer stars) {

    static public GithubRepoResponse of(GithubRepoDto githubRepoDto) {
        return new GithubRepoResponse(githubRepoDto.name(), githubRepoDto.stars());
    }
}

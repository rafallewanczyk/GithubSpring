package pl.allegro.github.controller;

import java.util.Map;

public record GithubPopularReposResponse(Map<String, Integer> popularRepos) {
}

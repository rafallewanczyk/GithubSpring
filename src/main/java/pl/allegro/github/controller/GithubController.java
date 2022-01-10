package pl.allegro.github.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.github.domain.GithubService;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/v1/github/{user}")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/repos")
    public List<GithubRepoResponse> getRepos(@PathVariable("user") String user) {
        var repos = githubService.getRepos(user);
        return Arrays.stream(repos).map(GithubRepoResponse::of).toList();
    }

    @GetMapping("/stars")
    public GithubStarsResponse getStars(@PathVariable("user") String user) {
        var stars = githubService.sumStars(user);
        return new GithubStarsResponse(stars);
    }

    @GetMapping("/popular")
    public GithubPopularReposResponse getPopular(@PathVariable("user") String user) {
        var popularRepos = githubService.groupByLanguage(user);
        return new GithubPopularReposResponse(popularRepos);
    }

}

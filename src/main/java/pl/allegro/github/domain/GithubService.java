package pl.allegro.github.domain;

import org.springframework.stereotype.Service;
import pl.allegro.github.adapter.GithubAdapter;
import pl.allegro.github.adapter.GithubRepoDto;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class GithubService {

    private final GithubAdapter githubAdapter;
    public static final String UNKNOWN_LANGUAGE = "unknown_language";

    public GithubService(GithubAdapter githubAdapter) {
        this.githubAdapter = githubAdapter;
    }

    public GithubRepoDto[] getRepos(String user) {
        return githubAdapter.getRepos(user);
    }

    public Integer sumStars(String user) {
        GithubRepoDto[] repos = githubAdapter.getRepos(user);
        return Arrays.stream(repos).filter(repo -> repo.stars() != null).mapToInt(GithubRepoDto::stars).sum();
    }

    public Map<String, Integer> groupByLanguage(String user) {
        GithubRepoDto[] repos = githubAdapter.getRepos(user);

        return Arrays.stream(repos)
                .collect(groupingBy(
                        repo -> repo.language() != null ? repo.language() : UNKNOWN_LANGUAGE,
                        Collectors.summingInt(repo -> {
                            return repo.sizeInBytes() != null ? repo.sizeInBytes() : 0;
                        }))
                );
    }
}

package pl.allegro.github.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.allegro.github.domain.GithubErrorHandler;

@Service
public class GithubAdapter {

    private final RestTemplate restTemplate;

    public GithubAdapter(RestTemplateBuilder restTemplateBuilder, @Value("${api.github.url}") String url) {
        restTemplate = restTemplateBuilder.rootUri(url).errorHandler(new GithubErrorHandler()).build();
    }

    public GithubRepoDto[] getRepos(String user) {
        String resourceUrl = "/users/" + user + "/repos";
        ResponseEntity<GithubRepoDto[]> response = restTemplate.getForEntity(resourceUrl, GithubRepoDto[].class);

        return response.getBody();
    }
}
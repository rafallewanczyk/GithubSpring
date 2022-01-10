package pl.allegro.github.domain

import pl.allegro.github.adapter.GithubRepoDto

class GithubRepoDtoBuilder {
    private Integer stars;
    private String language;
    private Integer sizeInBytes;

    GithubRepoDtoBuilder withStars(Integer stars) {
        this.stars = stars;
        return this;
    }

    GithubRepoDtoBuilder withSizeInBytes(Integer sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
        return this;
    }

    GithubRepoDtoBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    GithubRepoDto build() {
        return new GithubRepoDto(null, stars, language, sizeInBytes)
    }

}

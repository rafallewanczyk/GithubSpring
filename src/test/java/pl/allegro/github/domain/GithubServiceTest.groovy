package pl.allegro.github.domain

import pl.allegro.github.adapter.GithubAdapter
import spock.lang.Specification
import spock.lang.Unroll

class GithubServiceTest extends Specification {

    GithubAdapter githubAdapter = Mock()
    GithubService githubService = new GithubService(githubAdapter)

    @Unroll
    def "SumStars when returned dtos is #returnedDtos than sum should be #sum"() {
        when:
        githubAdapter.getRepos('user_name') >> returnedDtos

        then:
        githubService.sumStars('user_name') == sum

        where:
        returnedDtos                                                                                          | sum
        [new GithubRepoDtoBuilder().withStars(10).build(), new GithubRepoDtoBuilder().withStars(3).build()]   | 13
        [new GithubRepoDtoBuilder().withStars(18).build(), new GithubRepoDtoBuilder().withStars(1).build()]   | 19
        [new GithubRepoDtoBuilder().withStars(null).build(), new GithubRepoDtoBuilder().withStars(1).build()] | 1
        []                                                                                                    | 0

    }

    @Unroll
    def "GroupByLanguage when return dtos is #returnedDtos than popularRepos should be #popularRepos"() {
        when:
        githubAdapter.getRepos('user_name') >> returnedDtos

        then:
        githubService.groupByLanguage('user_name') == popularRepos

        where:
        returnedDtos                                                                                                                                                  | popularRepos
        [new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(10).build(), new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(1).build()] | ['python': 11]
        [new GithubRepoDtoBuilder().withLanguage(null).withSizeInBytes(10).build(), new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(1).build()]     | ['python': 1, 'unknown_language': 10]
        [new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(10).build(),
         new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(1).build(),
         new GithubRepoDtoBuilder().withLanguage('java').withSizeInBytes(8).build()]                                                                                  | ['python': 11, 'java': 8]
        []                                                                                                                                                            | [:]
        [new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(null).build(),
         new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(1).build()]                                                                                | ['python': 1]
        [new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(null).build(),
         new GithubRepoDtoBuilder().withLanguage('python').withSizeInBytes(null).build()]                                                                             | ['python': 0]

    }
}

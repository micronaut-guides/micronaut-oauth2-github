package example.micronaut;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;

import javax.annotation.Nullable;
import javax.validation.constraints.Pattern;
import java.util.List;

@Header(name = "User-Agent", value = "https://micronautguides.com")
@Header(name = "Accept", value = "application/vnd.github.v3+json, application/json")
@Client("https://api.github.com")
public interface GithubApiClient {

    @Get("/user")
    Flowable<GithubUser> getUser(@Header("Authorization") String authorization);

    @Get("/user/repos{?sort,direction}")
    List<GithubRepo> repos(@Pattern(regexp = "created|updated|pushed|full_name") @Nullable @QueryValue String sort,
                           @Pattern(regexp = "asc|desc") @Nullable @QueryValue String direction, @Header(HttpHeaders.AUTHORIZATION) String authorization);
}

package example.micronaut;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.oauth2.endpoint.token.response.OauthUserDetailsMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import org.reactivestreams.Publisher;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("github")
@Singleton
public class GithubUserDetailsMapper implements OauthUserDetailsMapper {

    private final GithubApiClient apiClient;

    public GithubUserDetailsMapper(GithubApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Publisher<UserDetails> createUserDetails(TokenResponse tokenResponse) {
        return apiClient.getUser("token " + tokenResponse.getAccessToken())
                .map(user -> {
                    List<String> roles = Collections.singletonList("ROLE_GITHUB");
                    Map<String, Object> atttributes = new HashMap<>();
                    atttributes.put(OauthUserDetailsMapper.ACCESS_TOKEN_KEY, tokenResponse.getAccessToken());
                    return new UserDetails(user.getLogin(), roles, atttributes);
                });
    }
}

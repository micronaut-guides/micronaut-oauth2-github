package example.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.oauth2.endpoint.token.response.OauthUserDetailsMapper;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/repos")
public class ReposController {

    private final GithubApiClient githubApiClient;

    public ReposController(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @View("repos")
    @Get
    Map<String, Object> index(Authentication authentication) {
        String authorization = null;

        Object claim = authentication.getAttributes().get(OauthUserDetailsMapper.ACCESS_TOKEN_KEY);
        if (claim instanceof String) {
            authorization = "Bearer " + claim.toString();
        }
        List<GithubRepo> repos = githubApiClient.repos("created", "desc", authorization);

        Map<String, Object> model = new HashMap<>();
        model.put("repos", repos);
        return model;
    }
}

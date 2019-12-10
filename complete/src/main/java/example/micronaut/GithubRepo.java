package example.micronaut;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class GithubRepo {

    String name;

    public GithubRepo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

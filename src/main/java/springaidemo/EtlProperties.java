package springaidemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.HashSet;
import java.util.Set;

@ConfigurationProperties(prefix = "app.etl")
public class EtlProperties {
    private Set<String> ignoredPaths = new HashSet<>();

    public Set<String> getIgnoredPaths() {
        return ignoredPaths;
    }

    public void setIgnoredPaths(Set<String> ignoredPaths) {
        this.ignoredPaths = ignoredPaths;
    }
}
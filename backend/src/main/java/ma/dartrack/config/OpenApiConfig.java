package ma.dartrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI darTrackOpenApi() {
        return new OpenAPI().info(new Info()
                .title("DarTrack API")
                .description("API REST du prototype local DarTrack")
                .version("v1"));
    }

    @Bean
    public GroupedOpenApi darTrackApiGroup() {
        return GroupedOpenApi.builder()
                .group("dartrack-v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}

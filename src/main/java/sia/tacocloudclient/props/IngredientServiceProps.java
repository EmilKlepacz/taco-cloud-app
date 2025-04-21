package sia.tacocloudclient.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
public class IngredientServiceProps {
    private String baseUrl;
}

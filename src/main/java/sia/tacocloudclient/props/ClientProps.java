package sia.tacocloudclient.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
@Data
public class ClientProps {
    private String baseUrl;
}

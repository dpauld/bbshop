package group7.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "pagination")
//@ConfigurationPropertiesScan
public class PaginationProperties {
    private int beveragePageSize=6;
    private int userPageSize=10;
    private int orderPageSize=10;
    private int pageNumber=1;
    private String sortBy = "id";
    private String sortDir = "asc";
}

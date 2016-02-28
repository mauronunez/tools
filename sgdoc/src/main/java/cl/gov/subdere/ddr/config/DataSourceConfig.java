package cl.gov.subdere.ddr.config;

import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public interface DataSourceConfig {
    DataSource dataSource();
}
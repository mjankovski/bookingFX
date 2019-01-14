package fx.booking.configuration;

import com.mysql.cj.jdbc.MysqlDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("fx.booking")
@PropertySource("classpath:dbconfig.properties")
public class SpringJDBCConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName(env.getProperty("db.name"));
        dataSource.setServerName(env.getProperty("db.servername"));
        dataSource.setUser(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
package graphql.kickstart.graphiql.boot.test;

import graphql.kickstart.graphiql.boot.GraphiQLAutoConfiguration;
import graphql.kickstart.graphiql.boot.GraphiQLController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Andrew Potter
 */
public class GraphiQLControllerTest extends AbstractAutoConfigurationTest {

    public GraphiQLControllerTest() {
        super(AnnotationConfigWebApplicationContext.class, GraphiQLAutoConfiguration.class);
    }

    @Configuration
    @PropertySource("classpath:enabled-config.properties")
    static class EnabledConfiguration {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    @Configuration
    @PropertySource("classpath:disabled-config.properties")
    static class DisabledConfiguration {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    @Test
    public void graphiqlLoads() {
        load(EnabledConfiguration.class);

        assertThat(this.getContext().getBean(GraphiQLController.class)).isNotNull();
    }

    @Test
    public void graphiqlDoesNotLoad() {
        load(DisabledConfiguration.class);

        assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
            .isThrownBy(() -> this.getContext().getBean(GraphiQLController.class));
    }
}

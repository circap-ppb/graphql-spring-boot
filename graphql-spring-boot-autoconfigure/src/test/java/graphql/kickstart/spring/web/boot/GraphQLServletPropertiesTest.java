package graphql.kickstart.spring.web.boot;

import graphql.kickstart.execution.context.ContextSetting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static graphql.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"graphql.servlet.mapping=/test", "graphql.servlet.contextSetting=PER_REQUEST_WITH_INSTRUMENTATION"})
public class GraphQLServletPropertiesTest {

    @Autowired
    private GraphQLServletProperties properties;

    @Test
    public void contains_custom_servlet_endpoint() {
        String mapping = properties.getMapping();
        assertNotNull(mapping);
        assertThat(mapping).isEqualTo("/test");
    }

    @Test
    public void containsCorrectContextSetting() {
        ContextSetting contextSetting = properties.getContextSetting();
        assertThat(contextSetting).isEqualTo(ContextSetting.PER_REQUEST_WITH_INSTRUMENTATION);
    }
}

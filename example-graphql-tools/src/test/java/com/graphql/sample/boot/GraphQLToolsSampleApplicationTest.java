package com.graphql.sample.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.graphql.spring.boot.test.GraphQLTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static graphql.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@GraphQLTest
public class GraphQLToolsSampleApplicationTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @Disabled
    public void get_comments() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/post-get-comments.graphql");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.post.id")).isEqualTo("1");
    }

    @Test
    public void get_comments_withFragments() throws IOException {
        List<String> fragments = new ArrayList<>();
        fragments.add("graphql/all-comment-fields-fragment.graphql");
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/post-get-comments-with-fragment.graphql", fragments);
        assertNotNull(response);
        assertThat((response.isOk())).isTrue();
        assertThat(response.get("$.data.post.id")).isEqualTo("1");
    }

    @Test
    public void create_post() throws IOException {
        ObjectNode variables = new ObjectMapper().createObjectNode();
        variables.put("text", "lorem ipsum dolor sit amet");
        GraphQLResponse response = graphQLTestTemplate.perform("graphql/create-post.graphql", variables);
        assertThat(response).isNotNull();
        assertThat(response.get("$.data.createPost.id")).isNotNull();
    }

}

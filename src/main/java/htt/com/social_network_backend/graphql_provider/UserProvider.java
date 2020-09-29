package htt.com.social_network_backend.graphql_provider;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import htt.com.social_network_backend.datafetcher.UserDataFetcher;

@Service
public class UserProvider {

        private GraphQL graphQL;

        @Autowired
        UserDataFetcher userDataFetcher;

        @Value("classpath:graphql/user.graphql")
        Resource resource;

        // load schema at application start up
        @PostConstruct
        private void loadSchema() throws IOException {
                // get the schema
                File schemaFile = resource.getFile();
                // parse schema
                TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
                RuntimeWiring wiring = buildRuntimeWiring();
                GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
                graphQL = GraphQL.newGraphQL(schema).build();
        }

        private RuntimeWiring buildRuntimeWiring() {
                return RuntimeWiring.newRuntimeWiring()
                        .type("Query", typeWiring -> typeWiring
                                .dataFetcher("getAll", userDataFetcher.getAll())
                                .dataFetcher("getById", userDataFetcher.getById()))
                        .build();
        }

        public GraphQL getGraphQL() {
                return graphQL;
        }
}

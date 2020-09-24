package htt.com.social_network_backend.graphqlservice;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import htt.com.social_network_backend.model.UserModel;
import htt.com.social_network_backend.service.UserService;

@Service
public class UserGraphQLService {

        private GraphQL graphQL;

        @Autowired
        UserService userService;

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
                                .dataFetcher("getAll", new DataFetcher<List<UserModel>>() {
                                        @Override
                                        public List<UserModel> get(DataFetchingEnvironment environment) {
                                                // String isn = environment.getArgument("id");
                                                return userService.getAll();

                                        }
                                }))
                        .build();
        }

        public GraphQL getGraphQL() {
                return graphQL;
        }
}

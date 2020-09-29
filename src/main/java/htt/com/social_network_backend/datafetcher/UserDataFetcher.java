package htt.com.social_network_backend.datafetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import htt.com.social_network_backend.model.UserModel;
import htt.com.social_network_backend.service.UserService;

@Component
public class UserDataFetcher {
	@Autowired
	UserService userService;

	public DataFetcher<List<UserModel>> getAll(){
		return dataFetchingEnvironment -> {
			return userService.getAll();
		};
	}

	public DataFetcher<UserModel> getById(){
		return dataFetchingEnvironment -> {
			Integer id = dataFetchingEnvironment.getArgument("id");
			return userService.getById(id);
		};
	}
}
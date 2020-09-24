package htt.com.social_network_backend.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import htt.com.social_network_backend.model.UserModel;

@Mapper
public interface UserMapper {
	public List<UserModel> getAllUser();
	
}
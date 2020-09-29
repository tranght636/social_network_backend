package htt.com.social_network_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import htt.com.social_network_backend.mapper.UserMapper;
import htt.com.social_network_backend.model.UserModel;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	
	public List<UserModel> getAll(){
		return userMapper.getAllUser();
	}

	public UserModel getById(Integer id){
		return userMapper.getById(id);
	}
}

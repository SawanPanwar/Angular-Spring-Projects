package com.rays.service;

import com.rays.common.BaseServiceInt;
import com.rays.dto.UserDTO;

public interface UserServiceInt extends BaseServiceInt<UserDTO> {

	public UserDTO authenticate(String loginId, String password);

	public UserDTO findByLogin(String loginId);

}

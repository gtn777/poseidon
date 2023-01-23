package com.nnk.springboot.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository dao;

	@Autowired
	ModelMapper mapper;

	public List<UserDto> getAll() {
		List<UserDto> returnedDto = dao.findAll()
				.stream()
				.map(el -> mapper.map(el, UserDto.class))
				.collect(Collectors.toList());
		return returnedDto;
	}

	public UserDto getById(int id) {
		UserDto returnedDto = mapper.map(dao.findById(id).get(),
				UserDto.class);
		return returnedDto;
	}

	public UserDto create(UserDto dto) {
		User newEntity = dao.save(mapper.map(dto, User.class));
		UserDto returnedDto = mapper.map(newEntity, UserDto.class);

		return returnedDto;
	}

	public UserDto updateById(int id, UserDto dto) {
		User toBeUpdateEntity = mapper.map(dto, User.class);
		UserDto returnedDto = mapper.map(dao.save(toBeUpdateEntity), UserDto.class);
		return returnedDto;
	}

	public void delete(int id) {
		dao.deleteById(id);
	}

}

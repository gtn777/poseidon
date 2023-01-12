package com.nnk.springboot.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
@Transactional
public class RuleNameService{

	@Autowired
	RuleNameRepository dao;

	@Autowired
	ModelMapper mapper;

	public List<RuleNameDto> getAll() {
		List<RuleNameDto> returnedDto = dao.findAll()
				.stream()
				.map(el -> mapper.map(el, RuleNameDto.class))
				.collect(Collectors.toList());
		return returnedDto;
	}

	public RuleNameDto getById(int id) {
		RuleNameDto returnedDto = mapper.map(dao.findById(id).get(),
				RuleNameDto.class);
		return returnedDto;
	}

	public RuleNameDto create(RuleNameDto dto) {
		RuleName newEntity = dao.save(mapper.map(dto, RuleName.class));
		RuleNameDto returnedDto = mapper.map(newEntity, RuleNameDto.class);
		return returnedDto;
	}

	public RuleNameDto updateById(int id, RuleNameDto dto) {
		RuleName toBeUpdateEntity = mapper.map(dto, RuleName.class);
		RuleNameDto returnedDto = mapper.map(dao.save(toBeUpdateEntity), RuleNameDto.class);
		return returnedDto;
	}

	public void delete(int id) {
		dao.deleteById(id);
	}
}

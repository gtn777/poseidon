package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.repositories.RuleNameRepository;

@RunWith(MockitoJUnitRunner.class)
public class RuleNameServiceTest {
	@InjectMocks
	RuleNameService service = new RuleNameService();

	@Mock
	RuleNameRepository dao;

	@Mock
	ModelMapper modelMapper;

	private RuleName entity;
	private RuleNameDto dto;
	private List<RuleName> entityList;
	private List<RuleNameDto> dtoList;

	@Before
	public void init() {
		entity = new RuleName();
		entity.setId(10);
		entity.setDescription("description");
		entity.setJson("json");
		entity.setName("name");
		entity.setSqlPart("sqlPart");
		entity.setTemplate("template");
		entity.setSqlStr("sqlStr");
		entityList = new ArrayList<RuleName>();
		entityList.add(entity);
		dto = new RuleNameDto();
		dto.setId(10);
		dto.setDescription("description");
		dto.setJson("json");
		dto.setName("name");
		dto.setSqlPart("sqlPart");
		dto.setTemplate("template");
		dto.setSqlStr("sqlStr");
		dtoList = new ArrayList<>();
		dtoList.add(dto);
	}

	@After
	public void afterEach() {
		verifyNoMoreInteractions(dao, modelMapper);
	}

	@Test
	public void getAll_thenReturnDtoList() {
		when(dao.findAll()).thenReturn(entityList);
		when(modelMapper.map(entity, RuleNameDto.class)).thenReturn(dto);

		List<?> result = service.getAll();

		verify(modelMapper, times(1)).map(entity, RuleNameDto.class);
		verify(dao, times(1)).findAll();
		assertEquals(dto, result.get(0));
		assertEquals(dtoList, result);
	}

	@Test
	public void getById_thenReturnDto() {
		Optional<RuleName> optionalEntity = Optional.of(entity);
		when(dao.findById(entity.getId())).thenReturn(optionalEntity);
		when(modelMapper.map(entity, RuleNameDto.class)).thenReturn(dto);

		Object result = service.getById(10);

		verify(modelMapper, times(1)).map(entity, RuleNameDto.class);
		verify(dao, times(1)).findById(10);
		assertEquals(dto, result);
	}

	@Test
	public void create_thenReturnDto() {
		when(dao.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, RuleName.class)).thenReturn(entity);
		when(modelMapper.map(entity, RuleNameDto.class)).thenReturn(dto);

		Object result = service.create(dto);

		verify(modelMapper, times(1)).map(dto, RuleName.class);
		verify(modelMapper, times(1)).map(entity, RuleNameDto.class);
		verify(dao, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void updateById_thenReturnDto() {
		when(dao.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, RuleName.class)).thenReturn(entity);
		when(modelMapper.map(entity, RuleNameDto.class)).thenReturn(dto);

		Object result = service.updateById(10, dto);

		verify(modelMapper, times(1)).map(dto, RuleName.class);
		verify(modelMapper, times(1)).map(entity, RuleNameDto.class);
		verify(dao, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void deleteById_noReturn() {
		service.delete(10);

		verify(dao, times(1)).deleteById(10);
	}
}

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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;

@RunWith(MockitoJUnitRunner.class)
public class CurvePointServiceTest {

	@InjectMocks
	CurvePointService service = new CurvePointService();

	@Mock
	CurvePointRepository dao;

	@Mock
	ModelMapper modelMapper;

	private CurvePoint entity;
	private CurvePointDto dto;
	private List<CurvePoint> entityList;
	private List<CurvePointDto> dtoList;

	@Before
	public void init() {
		entity = new CurvePoint();
		entity.setCurveId(25);
		entity.setId(10);
		entity.setTerm(15.0);
		entity.setValue(15.0);
		entityList = new ArrayList<CurvePoint>();
		entityList.add(entity);
		dto = new CurvePointDto();
		dto.setCurveId(25);
		dto.setId(10);
		dto.setTerm(15.0);
		dto.setValue(15.0);
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
		when(modelMapper.map(entity, CurvePointDto.class)).thenReturn(dto);

		List<?> result = service.getAll();

		verify(modelMapper, times(1)).map(entity, CurvePointDto.class);
		verify(dao, times(1)).findAll();
		assertEquals(dto, result.get(0));
		assertEquals(dtoList, result);
	}

	@Test
	public void getById_thenReturnDto() {
		Optional<CurvePoint> optionalEntity = Optional.of(entity);
		when(dao.findById(entity.getId())).thenReturn(optionalEntity);
		when(modelMapper.map(entity, CurvePointDto.class)).thenReturn(dto);

		Object result = service.getById(10);

		verify(modelMapper, times(1)).map(entity, CurvePointDto.class);
		verify(dao, times(1)).findById(10);
		assertEquals(dto, result);
	}

	@Test
	public void create_thenReturnDto() {
		when(dao.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, CurvePoint.class)).thenReturn(entity);
		when(modelMapper.map(entity, CurvePointDto.class)).thenReturn(dto);

		Object result = service.create(dto);

		verify(modelMapper, times(1)).map(dto, CurvePoint.class);
		verify(modelMapper, times(1)).map(entity, CurvePointDto.class);
		verify(dao, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void updateById_thenReturnDto() {
		when(dao.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, CurvePoint.class)).thenReturn(entity);
		when(modelMapper.map(entity, CurvePointDto.class)).thenReturn(dto);

		Object result = service.updateById(10, dto);

		verify(modelMapper, times(1)).map(dto, CurvePoint.class);
		verify(modelMapper, times(1)).map(entity, CurvePointDto.class);
		verify(dao, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void deleteById_noReturn() {
		service.delete(10);

		verify(dao, times(1)).deleteById(10);
	}

}

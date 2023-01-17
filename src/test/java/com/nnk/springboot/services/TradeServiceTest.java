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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {
	@InjectMocks
	TradeService service = new TradeService();

	@Mock
	TradeRepository dao;

	@Mock
	ModelMapper modelMapper;

	private Trade entity;
	private TradeDto dto;
	private List<Trade> entityList;
	private List<TradeDto> dtoList;

	@Before
	public void init() {
		entity = new Trade();
		entity.setId(10);
		entity.setAccount("account");
		entity.setBuyQuantity(15.0);
		entity.setType("type");
		entityList = new ArrayList<Trade>();
		entityList.add(entity);
		dto = new TradeDto();
		dto.setId(10);
		dto.setAccount("account");
		dto.setBuyQuantity(15.0);
		dto.setType("type");
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
		when(modelMapper.map(entity, TradeDto.class)).thenReturn(dto);

		List<?> result = service.getAll();

		verify(modelMapper, times(1)).map(entity, TradeDto.class);
		verify(dao, times(1)).findAll();
		assertEquals(dto, result.get(0));
		assertEquals(dtoList, result);
	}

	@Test
	public void getById_thenReturnDto() {
		Optional<Trade> optionalEntity = Optional.of(entity);
		when(dao.findById(entity.getId())).thenReturn(optionalEntity);
		when(modelMapper.map(entity, TradeDto.class)).thenReturn(dto);

		Object result = service.getById(10);

		verify(modelMapper, times(1)).map(entity, TradeDto.class);
		verify(dao, times(1)).findById(10);
		assertEquals(dto, result);
	}

	@Test
	public void create_thenReturnDto() {
		when(dao.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, Trade.class)).thenReturn(entity);
		when(modelMapper.map(entity, TradeDto.class)).thenReturn(dto);

		Object result = service.create(dto);

		verify(modelMapper, times(1)).map(dto, Trade.class);
		verify(modelMapper, times(1)).map(entity, TradeDto.class);
		verify(dao, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void updateById_thenReturnDto() {
		when(dao.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, Trade.class)).thenReturn(entity);
		when(modelMapper.map(entity, TradeDto.class)).thenReturn(dto);

		Object result = service.updateById(10, dto);

		verify(modelMapper, times(1)).map(dto, Trade.class);
		verify(modelMapper, times(1)).map(entity, TradeDto.class);
		verify(dao, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void deleteById_noReturn() {
		service.delete(10);

		verify(dao, times(1)).deleteById(10);
	}
}

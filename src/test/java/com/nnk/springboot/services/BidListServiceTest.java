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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;

@RunWith(MockitoJUnitRunner.class)
public class BidListServiceTest {

	@InjectMocks
	BidListService service = new BidListService();

	@Mock
	BidListRepository bidListRepository;

	@Mock
	ModelMapper modelMapper;

	private BidList entity;
	private BidListDto dto;
	private List<BidList> entityList;
	private List<BidListDto> dtoList;

	@Before
	public void init() {
		entity = new BidList();
		entity.setAccount("test account");
		entity.setBidListId(10);
		entity.setType("test type");
		entity.setBidQuantity(15.0);
		entityList = new ArrayList<BidList>();
		entityList.add(entity);
		dto = new BidListDto();
		dto.setAccount("test account");
		dto.setBidListId(10);
		dto.setType("test type");
		dto.setBidQuantity(15.0);
		dtoList = new ArrayList<>();
		dtoList.add(dto);
	}

	@After
	public void afterEach() {
		verifyNoMoreInteractions(bidListRepository, modelMapper);
	}

	@Test
	public void getAll_thenReturnDtoList() {
		when(bidListRepository.findAll()).thenReturn(entityList);
		when(modelMapper.map(entity, BidListDto.class)).thenReturn(dto);

		List<?> result = service.getAll();

		verify(modelMapper, times(1)).map(entity, BidListDto.class);
		verify(bidListRepository, times(1)).findAll();
		assertEquals(dto, result.get(0));
		assertEquals(dtoList, result);
	}

	@Test
	public void getById_thenReturnDto() {
		Optional<BidList> optionalEntity = Optional.of(entity);
		when(bidListRepository.findById(entity.getBidListId())).thenReturn(optionalEntity);
		when(modelMapper.map(entity, BidListDto.class)).thenReturn(dto);

		Object result = service.getById(10);

		verify(modelMapper, times(1)).map(entity, BidListDto.class);
		verify(bidListRepository, times(1)).findById(10);
		assertEquals(dto, result);
	}

	@Test
	public void create_thenReturnDto() {
		when(bidListRepository.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, BidList.class)).thenReturn(entity);
		when(modelMapper.map(entity, BidListDto.class)).thenReturn(dto);

		Object result = service.create(dto);

		verify(modelMapper, times(1)).map(dto, BidList.class);
		verify(modelMapper, times(1)).map(entity, BidListDto.class);
		verify(bidListRepository, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void updateById_thenReturnDto() {
		when(bidListRepository.save(entity)).thenReturn(entity);
		when(modelMapper.map(dto, BidList.class)).thenReturn(entity);
		when(modelMapper.map(entity, BidListDto.class)).thenReturn(dto);

		Object result = service.updateById(10, dto);

		verify(modelMapper, times(1)).map(dto, BidList.class);
		verify(modelMapper, times(1)).map(entity, BidListDto.class);
		verify(bidListRepository, times(1)).save(entity);
		assertEquals(dto, result);
	}

	@Test
	public void deleteById_noReturn() {
		service.delete(10);

		verify(bidListRepository, times(1)).deleteById(10);
	}

}
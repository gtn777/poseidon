package com.nnk.springboot.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;

@Service
@Transactional
public class TradeService {

	@Autowired
	TradeRepository dao;

	@Autowired
	ModelMapper mapper;

	public List<TradeDto> getAll() {
		List<TradeDto> returnedDto = dao.findAll()
				.stream()
				.map(el -> mapper.map(el, TradeDto.class))
				.collect(Collectors.toList());
		return returnedDto;
	}

	public TradeDto getById(int id) {
		TradeDto returnedDto = mapper.map(dao.findById(id).get(),
				TradeDto.class);
		return returnedDto;
	}

	public TradeDto create(TradeDto dto) {
		Trade newEntity = dao.save(mapper.map(dto, Trade.class));
		TradeDto returnedDto = mapper.map(newEntity, TradeDto.class);

		return returnedDto;
	}

	public TradeDto updateById(int id, TradeDto dto) {
		Trade toBeUpdateEntity = mapper.map(dto, Trade.class);
		TradeDto returnedDto = mapper.map(dao.save(toBeUpdateEntity), TradeDto.class);
		return returnedDto;
	}

	public void delete(int id) {
		dao.deleteById(id);
	}
}

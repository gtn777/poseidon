package com.nnk.springboot.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class BidListService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BidListRepository bidListRepository;

	public List<BidListDto> getAll() {
		List<BidListDto> allBidListDto = bidListRepository.findAll()
				.stream()
				.map(bid -> modelMapper.map(bid, BidListDto.class))
				.collect(Collectors.toList());
		return allBidListDto;
	}

	public BidListDto getById(int id) {
		BidListDto returnValue = modelMapper.map(bidListRepository.findById(id).get(),
				BidListDto.class);
		return returnValue;
	}

	public BidListDto create(BidListDto dto) {
		BidList newBidList = bidListRepository.save(modelMapper.map(dto, BidList.class));
		BidListDto updatedBidListDto = modelMapper.map(newBidList, BidListDto.class);
		return updatedBidListDto;
	}

	public BidListDto updateById(int id, BidListDto dto) {
		BidList bidListToUpdate = modelMapper.map(dto, BidList.class);
		return modelMapper.map(bidListRepository.save(bidListToUpdate), BidListDto.class);
	}

	public void delete(int id) {
		bidListRepository.deleteById(id);
	}

}

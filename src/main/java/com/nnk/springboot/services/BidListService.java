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

@Service
@Transactional
public class BidListService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BidListRepository bidListRepository;

	public List<BidListDto> getAll() {
		List<BidListDto> allBidListDto = bidListRepository.findAll().stream()
				.map(bid -> this.modelMapper.map(bid, BidListDto.class))
				.collect(Collectors.toList());
		return allBidListDto;
	}

	public BidListDto getById(int id) {
		return this.modelMapper.map(bidListRepository.findById(id).get(), BidListDto.class);
	}

	public BidListDto updateById(int id, BidListDto dto) {
		BidList bidListUpdated = this.modelMapper.map(dto,  BidList.class);
		bidListUpdated.setBidListId(id);
		return this.modelMapper.map(bidListRepository.save(bidListUpdated), BidListDto.class);
	}

	public Object create(BidListDto dto) {
		return bidListRepository.save(this.modelMapper.map(dto, BidList.class));
	}
	

}

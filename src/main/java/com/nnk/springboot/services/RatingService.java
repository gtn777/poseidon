package com.nnk.springboot.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.repositories.RatingRepository;

@Service
@Transactional
public class RatingService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RatingRepository dao;

	public List<RatingDto> getAll() {
		List<RatingDto> allRatingDto = dao.findAll()
				.stream()
				.map(bid -> modelMapper.map(bid, RatingDto.class))
				.collect(Collectors.toList());
		return allRatingDto;
	}

	public RatingDto getById(int id) {
		RatingDto returnValue = modelMapper.map(dao.findById(id).get(), RatingDto.class);
		return returnValue;
	}

	public RatingDto create(RatingDto dto) {
		Rating newRating = dao.save(modelMapper.map(dto, Rating.class));
		RatingDto updatedRatingDto = modelMapper.map(newRating, RatingDto.class);
		return updatedRatingDto;
	}

	public RatingDto updateById(int id, RatingDto dto) {
		Rating RatingToUpdate = modelMapper.map(dto, Rating.class);
		return modelMapper.map(dao.save(RatingToUpdate), RatingDto.class);
	}

	public void delete(int id) {
		dao.deleteById(id);
	}
}

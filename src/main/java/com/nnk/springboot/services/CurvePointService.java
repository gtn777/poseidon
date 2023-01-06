package com.nnk.springboot.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
@Transactional
public class CurvePointService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CurvePointRepository dao;

	public List<CurvePointDto> getAll() {
		List<CurvePointDto> allBidListDto = dao.findAll()
				.stream()
				.map(curve -> modelMapper.map(curve, CurvePointDto.class))
				.collect(Collectors.toList());
		return allBidListDto;
	}

	public CurvePointDto getById(int id) {
		CurvePointDto returnValue = modelMapper.map(dao.findById(id).get(), CurvePointDto.class);
		return returnValue;
	}

	public CurvePointDto create(CurvePointDto dto) {
		CurvePoint newCurvePoint = dao.save(modelMapper.map(dto, CurvePoint.class));
		CurvePointDto updatedBidListDto = modelMapper.map(newCurvePoint, CurvePointDto.class);
		return updatedBidListDto;
	}

	public CurvePointDto updateById(int id, CurvePointDto dto) {
		CurvePoint bidListToUpdate = modelMapper.map(dto, CurvePoint.class);
		return modelMapper.map(dao.save(bidListToUpdate), CurvePointDto.class);
	}

	public void delete(int id) {
		dao.deleteById(id);
	}

}

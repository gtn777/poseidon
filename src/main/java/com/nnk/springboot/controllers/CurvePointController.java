package com.nnk.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.services.CurvePointService;

@Controller
public class CurvePointController {
	@Autowired
	private CurvePointService service;

	@GetMapping("/curvePoint/list")
	public String getList(Model model) {
		List<CurvePointDto> allCurvePointDto = service.getAll();
		model.addAttribute("allCurvePointDto", allCurvePointDto);
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String getAdd(Model model) {
		CurvePointDto curvePointDto = new CurvePointDto();
		model.addAttribute(curvePointDto);
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String postValidate(@Valid CurvePointDto curvePointDto, BindingResult result) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		} else {
			service.create(curvePointDto);
		}
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String postUpdate(@PathVariable("id") Integer id,
			CurvePointDto curvePointDto,
			Model model) {
		curvePointDto = service.getById(id);
		model.addAttribute("curvePointDto", curvePointDto);
		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public Object getUpdate(@PathVariable("id") Integer id,
			@Valid CurvePointDto curvePointDto,
			BindingResult result) {
		curvePointDto.setId(id);
		if (result.hasErrors()) {
			return "curvePoint/update";
		} else {
			service.updateById(id, curvePointDto);
		}
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String getDelete(@PathVariable("id") Integer id, Model model) {
		service.delete(id);
		return "redirect:/curvePoint/list";
	}
}

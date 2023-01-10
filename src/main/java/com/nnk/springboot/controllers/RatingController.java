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

import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.services.RatingService;

@Controller
public class RatingController {
	@Autowired
	private RatingService service;

	@GetMapping("/rating/list")
	public String getList(Model model) {
		List<RatingDto> allRatingDto = service.getAll();
		model.addAttribute("allRatingDto", allRatingDto);
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String getAdd(Model model) {
		RatingDto ratingDto = new RatingDto();
		model.addAttribute(ratingDto);
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String postValidate(@Valid RatingDto ratingDto, BindingResult result) {
		if (result.hasErrors()) {
			return "rating/add";
		} else {
			service.create(ratingDto);
		}
		return "redirect:/rating/list";
	}

	@GetMapping("/rating/update/{id}")
	public String getUpdate(@PathVariable("id") Integer id, RatingDto ratingDto, Model model) {
		ratingDto = service.getById(id);
		model.addAttribute("ratingDto", ratingDto);
		return "rating/update";
	}

	@PostMapping("/rating/update/{id}")
	public Object postUpdate(@PathVariable("id") Integer id,
			@Valid RatingDto ratingDto,
			BindingResult result) {
		ratingDto.setId(id);
		if (result.hasErrors()) {
			return "rating/update";
		} else {
			service.updateById(id, ratingDto);
		}
		return "redirect:/rating/list";
	}

	@GetMapping("/rating/delete/{id}")
	public String getDelete(@PathVariable("id") Integer id, Model model) {
		service.delete(id);
		return "redirect:/rating/list";
	}
}

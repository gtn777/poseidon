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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RatingController {
	@Autowired
	private RatingService service;

	@GetMapping("/rating/list")
	public String getList(Model model) {
		List<RatingDto> allRatingDto = service.getAll();
		model.addAttribute("allRatingDto", allRatingDto);
		log.debug("GET /rating/list");
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String getAdd(Model model) {
		RatingDto ratingDto = new RatingDto();
		model.addAttribute(ratingDto);
		log.debug("GET /rating/add");
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String postValidate(@Valid RatingDto ratingDto, BindingResult result) {
		if (result.hasErrors()) {
			log.error("POST /rating/validate FIELDS ERROR");
			return "rating/add";
		} else {
			service.create(ratingDto);
			log.debug("POST /rating/validate ratingDto:" + ratingDto.toString());
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
			log.error("POST /rating/update FIELDS ERROR");
			return "rating/update";
		} else {
			service.updateById(id, ratingDto);
			log.debug(
					"POST /rating/update/" + id + " ratingDto:" + ratingDto.toString());
		}
		return "redirect:/rating/list";
	}

	@GetMapping("/rating/delete/{id}")
	public String getDelete(@PathVariable("id") Integer id, Model model) {
		service.delete(id);
		log.debug("GET /rating/delete/" + id);
		return "redirect:/rating/list";
	}
}

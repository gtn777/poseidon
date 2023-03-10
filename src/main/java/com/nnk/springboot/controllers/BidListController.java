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

import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.services.BidListService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BidListController {

	@Autowired
	private BidListService bidListService;

	@GetMapping("/bidList/list")
	public String getList(Model model) {
		List<BidListDto> allBidListDto = bidListService.getAll();
		model.addAttribute("allBidListDto", allBidListDto);
		log.debug("GET /bidList/list ");
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String getAdd(Model model) {
		BidListDto bidListDto = new BidListDto();
		model.addAttribute(bidListDto);
		log.debug("GET /bidList/add ");
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String postValidate(@Valid BidListDto bidListDto, BindingResult result) {
		if (result.hasErrors()) {
			log.error("POST /bidList/add FIELDS ERROR ");
			return "bidList/add";
		} else {
			log.debug("POST /bidList/add bidListDto:" + bidListDto.toString());
			bidListService.create(bidListDto);
		}
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/update/{id}")
	public String getUpdate(@PathVariable("id") Integer id,
			BidListDto bidListDto,
			Model model) {
		bidListDto = bidListService.getById(id);
		model.addAttribute("bidListDto", bidListDto);
		log.debug("GET /bidList/update/" + id);
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public Object postUpdate(@PathVariable("id") Integer id,
			@Valid BidListDto bidListDto,
			BindingResult result) {
		bidListDto.setBidListId(id);
		if (result.hasErrors()) {
			log.error("POST /bidList/update/" + id + " FIELDS ERROR ");
			return "bidList/update";
		} else {
			bidListService.updateById(id, bidListDto);
			log.debug("POST /bidList/update/" + id + " bidListDto:" + bidListDto.toString());
		}
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/delete/{id}")
	public String getDelete(@PathVariable("id") Integer id, Model model) {
		bidListService.delete(id);
		log.debug("GET /bidList/delete/" + id);
		return "redirect:/bidList/list";
	}

}

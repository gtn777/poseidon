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

import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.services.TradeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TradeController {

	@Autowired
	private TradeService service;

	@GetMapping("/trade/list")
	public String getList(Model model) {
		List<TradeDto> allTradeDto = service.getAll();
		model.addAttribute("allTradeDto", allTradeDto);
		log.debug("GET /trade/list");
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String getAdd(Model model) {
		TradeDto tradeDto = new TradeDto();
		model.addAttribute("tradeDto", tradeDto);
		log.debug("GET /trade/add");
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String postValidate(@Valid TradeDto tradeDto, BindingResult result) {
		if (result.hasErrors()) {
			log.error("POST /trade/validate FIELDS ERROR");
			return "trade/add";
		} else {
			service.create(tradeDto);
			log.debug("POST /trade/validate tradeDto:" + tradeDto.toString());
		}
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/update/{id}")
	public String getUpdate(@PathVariable("id") Integer id, Model model) {
		TradeDto tradeDto = service.getById(id);
		model.addAttribute("tradeDto", tradeDto);
		log.debug("GET /trade/update/" + id);
		return "trade/update";
	}

	@PostMapping("/trade/update/{id}")
	public Object postUpdate(@PathVariable("id") Integer id,
			@Valid TradeDto tradeDto,
			BindingResult result) {
		tradeDto.setId(id);
		if (result.hasErrors()) {
			log.error("POST /trade/update FIELDS ERROR");
			return "trade/update";
		} else {
			service.updateById(id, tradeDto);
			log.debug(
					"POST /trade/update/" + id + " tradeDto:" + tradeDto.toString());
		}
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/delete/{id}")
	public String getDelete(@PathVariable("id") Integer id, Model model) {
		service.delete(id);
		log.debug("GET /trade/delete/" + id);
		return "redirect:/trade/list";
	}
}

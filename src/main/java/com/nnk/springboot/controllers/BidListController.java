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

@Controller
public class BidListController {

	@Autowired
	private BidListService bidListService;

	@GetMapping("/bidList/list")
	public String home(Model model) {
		// TODO: call service find all bids to show to the view
		List<BidListDto> allBidListDto = bidListService.getAll();
		model.addAttribute("allBidListDto", allBidListDto);
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidListDto bidListDto) {
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidListDto bidListDto, BindingResult result) {
		// TODO: check data valid and save to db, after saving return bid list
		if (result.hasErrors()) {
			return "bidList/add";
		} else {
			bidListService.create(bidListDto);
		}
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("bidListDto", bidListService.getById(id));
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidListDto bidListDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bidList/update/"+id;
		} else {
			bidListService.updateById(id, bidListDto);
		}
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		return "redirect:/bidList/list";
	}
}

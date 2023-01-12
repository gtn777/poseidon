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

import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.services.RuleNameService;

@Controller
public class RuleNameController {
	@Autowired
	private RuleNameService service;

	@GetMapping("/ruleName/list")
	public String getList(Model model) {
		List<RuleNameDto> allRuleNameDto = service.getAll();
		model.addAttribute("allRuleNameDto", allRuleNameDto);
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String getAdd(Model model) {
		RuleNameDto ruleNameDto = new RuleNameDto();
		model.addAttribute("ruleNameDto", ruleNameDto);
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String postValidate(@Valid RuleNameDto ruleNameDto, BindingResult result) {
		if (result.hasErrors()) {
			return "ruleName/add";
		} else {
			service.create(ruleNameDto);
		}
		return "redirect:/ruleName/list";
	}

	@GetMapping("/ruleName/update/{id}")
	public String getUpdate(@PathVariable("id") Integer id, Model model) {
		RuleNameDto ruleNameDto = service.getById(id);
		model.addAttribute("ruleNameDto", ruleNameDto);
		return "ruleName/update";
	}

	@PostMapping("/ruleName/update/{id}")
	public Object postUpdate(@PathVariable("id") Integer id,
			@Valid RuleNameDto ruleNameDto,
			BindingResult result) {
		ruleNameDto.setId(id);
		if (result.hasErrors()) {
			return "ruleName/update";
		} else {
			service.updateById(id, ruleNameDto);
		}
		return "redirect:/ruleName/list";
	}

	@GetMapping("/ruleName/delete/{id}")
	public String getDelete(@PathVariable("id") Integer id, Model model) {
		service.delete(id);
		return "redirect:/ruleName/list";
	}
}

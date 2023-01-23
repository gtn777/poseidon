package com.nnk.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/user/list")
	public String getList(Model model) {
		List<UserDto> allUserDto = service.getAll();
		model.addAttribute("allUserDto", allUserDto);
		return "user/list";
	}

	@GetMapping("/user/add")
	public String getAdd(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String postValidate(@Valid UserDto userDto, BindingResult result) {
		if (result.hasErrors()) {
			return "user/add";
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userDto.setPassword(encoder.encode(userDto.getPassword()));
			service.create(userDto);
		}
		return "redirect:/user/list";
	}

	@GetMapping("/user/update/{id}")
	public String getUpdate(@PathVariable("id") Integer id, Model model) {
		UserDto userDto = service.getById(id);
		model.addAttribute("userDto", userDto);
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public Object postUpdate(@PathVariable("id") Integer id,
			@Valid UserDto userDto,
			BindingResult result) {
		userDto.setId(id);
		if (result.hasErrors()) {
			return "user/update";
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userDto.setPassword(encoder.encode(userDto.getPassword()));
			service.updateById(id, userDto);
		}
		return "redirect:/user/list";
	}

	@GetMapping("/user/delete/{id}")
	public String getDelete(@PathVariable("id") Integer id, Model model) {
		service.delete(id);
		return "redirect:/user/list";
	}
}

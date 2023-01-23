package com.nnk.springboot.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.TrueFalseType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.utilities.ObjectToMultivalueMap;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(secure = true)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService service;

	private List<UserDto> dtoList;

	private UserDto dto;

	private Integer idInteger;

	@Before
	public void beforeEachInit() {
		dtoList = new ArrayList<>();
		dto = new UserDto();
		dto.setFullname("FullnameTest");
		dto.setUsername("userNameTest");
		dto.setPassword("PasS*4587");
		dto.setRole("USER");
		idInteger = 10;
	}

	@After
	public void afterEachVerif() {
		verifyNoMoreInteractions(service);

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void get_tradeList_successfully_200AndViewOk() throws Exception {
		dto.setId(idInteger);
		dtoList.add(dto);
		when(service.getAll()).thenReturn(dtoList);
		MvcResult result = mvc.perform(get("/user/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/list"))
				.andReturn();
		verify(service, times(1)).getAll();
		assertTrue(result.getResponse().getContentAsString().contains("FullnameTest"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void get_addBidForm_successfully_200AndViewOk() throws Exception {
		mvc.perform(get("/user/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/add"))
				.andExpect(model().attributeExists("userDto"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void post_validate_successfully_dataValidatedAnd302() throws Exception {
		mvc.perform(post("/user/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/list"))
				.andDo(print());
		verify(service, times(1)).create(any(UserDto.class));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void get_tradeUpdate_succesfully_200AndViewOk() throws Exception {
		when(service.getById(idInteger)).thenReturn(dto);
		mvc.perform(get("/user/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(status().isOk())
				.andExpect(view().name("user/update"))
				.andExpect(model().attributeExists("userDto"))
				.andExpect(content().string(containsString("userNameTest")));
		verify(service, times(1)).getById(idInteger);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void post_tradeUpdate_successfully_dataValidAnd302() throws Exception {
		mvc.perform(post("/user/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/list"));
		verify(service, times(1)).updateById(anyInt(), any(UserDto.class));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void get_tradeDelete_successfully_dataValidAnd302() throws Exception {
		mvc.perform(get("/user/delete/{id}", idInteger).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/list"));
		verify(service, times(1)).delete(idInteger);
	}

	// -------------------------------------------------------------------------------------
	// ----- Evaluate the @valid annotation in controllers,
	// ----- request is send with invalid dto according the dto
	// ----- fields annotation. (@Min, @NotBlank, @NotNull ...)

	@Test
	@WithMockUser(roles = "ADMIN")
	public void post_tradeValidate_invalidDto_modelHasError() throws Exception {
		dto.setFullname("g");
		dto.setUsername("");
		dto.setPassword("PasS*hty");
		dto.setRole("USER");
		mvc.perform(post("/user/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model()
						.attributeHasFieldErrors("userDto",
								"username",
								"password",
								"fullname"))
				.andExpect(view().name("user/add"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void post_tradeUpdate_invalidDto_modelHasError() throws Exception {
		dto.setFullname("F");
		dto.setUsername("");
		dto.setPassword("PasS4587");
		dto.setRole("USER");
		mvc.perform(post("/user/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(
						model().attributeHasFieldErrors("userDto",
								"username",
								"password",
								"fullname"))
				.andExpect(view().name("user/update"));
	}
	// --------------------------------------------------------------------------------
	// ----- Evaluate security filter,
	// ----- request is send with unauthenticated user

	@Test
	public void getHome_anonymousUser_401() throws Exception {
		mvc.perform(get("/user/list")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_addBidForm_anonymousUser_401() throws Exception {
		mvc.perform(get("/user/add")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_validate_anonymousUser_401() throws Exception {
		mvc.perform(post("/user/validate").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_tradeUpdate_anonymousUser_401() throws Exception {
		mvc.perform(get("/user/update")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_tradeUpdate_anonymousUser_then401() throws Exception {
		mvc.perform(post("/user/update").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_tradedelete_anonymousUser_then401() throws Exception {
		mvc.perform(post("/user/delete").with(csrf())).andExpect(status().isUnauthorized());
	}

}

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

import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.utilities.ObjectToMultivalueMap;

@WithMockUser
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = RuleNameController.class)
@AutoConfigureMockMvc(secure = true)
public class RuleNameControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RuleNameService service;

	private List<RuleNameDto> dtoList;

	private RuleNameDto dto;

	private Integer idInteger;

	@Before
	public void beforeEachInit() {
		dtoList = new ArrayList<>();
		dto = new RuleNameDto();
		dto.setDescription("test descritpion");
		dto.setName("test name");
		dto.setJson("test json");
		dto.setSqlPart("test sql part");
		dto.setTemplate("test template");
		dto.setSqlStr("test sql string");
		idInteger = 10;
	}

	@After
	public void afterEachVerif() {
		verifyNoMoreInteractions(service);

	}

	@Test
	public void get_ruleNameList_successfully_200AndViewOk() throws Exception {
		dto.setId(idInteger);
		dtoList.add(dto);
		when(service.getAll()).thenReturn(dtoList);
		MvcResult result = mvc.perform(get("/ruleName/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/list"))
				.andReturn();
		verify(service, times(1)).getAll();
		assertTrue(result.getResponse().getContentAsString().contains("test"));
	}

	@Test
	public void get_addBidForm_successfully_200AndViewOk() throws Exception {
		mvc.perform(get("/ruleName/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/add"))
				.andExpect(model().attributeExists("ruleNameDto"));
	}

	@Test
	public void post_validate_successfully_dataValidatedAnd302() throws Exception {
		mvc.perform(post("/ruleName/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ruleName/list"))
				.andDo(print());
		verify(service, times(1)).create(any(RuleNameDto.class));
	}

	@Test
	public void get_ruleNameUpdate_succesfully_200AndViewOk() throws Exception {
		when(service.getById(idInteger)).thenReturn(dto);
		mvc.perform(get("/ruleName/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/update"))
				.andExpect(model().attributeExists("ruleNameDto"))
				.andExpect(content().string(containsString("test")));
		verify(service, times(1)).getById(idInteger);
	}

	@Test
	public void post_ruleNameUpdate_successfully_dataValidAnd302() throws Exception {
		mvc.perform(post("/ruleName/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ruleName/list"));
		verify(service, times(1)).updateById(anyInt(), any(RuleNameDto.class));
	}

	@Test
	public void get_ruleNameDelete_successfully_dataValidAnd302() throws Exception {
		mvc.perform(get("/ruleName/delete/{id}", idInteger).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ruleName/list"));
		verify(service, times(1)).delete(idInteger);
	}

	// -------------------------------------------------------------------------------------
	// ----- Evaluate the @valid annotation in controllers,
	// ----- request is send with invalid dto according the dto
	// ----- fields annotation. (@Min, @NotBlank, @NotNull ...)

	@Test
	public void post_ruleNameValidate_invalidDto_modelHasError() throws Exception {
		dto.setDescription(" ");
		dto.setTemplate(null);
		dto.setSqlStr("");
		mvc.perform(post("/ruleName/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("ruleNameDto",
						"description",
						"template",
						"sqlStr"))
				.andExpect(view().name("ruleName/add"));
	}

	@Test
	public void post_ruleNameUpdate_invalidDto_modelHasError() throws Exception {
		dto.setJson(" ");
		dto.setName(null);
		dto.setSqlPart("");
		mvc.perform(post("/ruleName/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(
						model().attributeHasFieldErrors("ruleNameDto", "name", "json", "sqlPart"))
				.andExpect(view().name("ruleName/update"));
	}
	// --------------------------------------------------------------------------------
	// ----- Evaluate security filter,
	// ----- request is send with unauthenticated user

	@Test
	@WithAnonymousUser
	public void getHome_anonymousUser_401() throws Exception {
		mvc.perform(get("/ruleName/list")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_addBidForm_anonymousUser_401() throws Exception {
		mvc.perform(get("/ruleName/add")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_validate_anonymousUser_401() throws Exception {
		mvc.perform(post("/ruleName/validate").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_ruleNameUpdate_anonymousUser_401() throws Exception {
		mvc.perform(get("/ruleName/update")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_ruleNameUpdate_anonymousUser_then401() throws Exception {
		mvc.perform(post("/ruleName/update").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_ruleNamedelete_anonymousUser_then401() throws Exception {
		mvc.perform(post("/ruleName/delete").with(csrf())).andExpect(status().isUnauthorized());
	}

}

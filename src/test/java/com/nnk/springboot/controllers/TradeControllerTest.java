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

import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.utilities.ObjectToMultivalueMap;

@WithMockUser
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = TradeController.class)
@AutoConfigureMockMvc(secure = true)
public class TradeControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TradeService service;

	private List<TradeDto> dtoList;

	private TradeDto dto;

	private Integer idInteger;

	@Before
	public void beforeEachInit() {
		dtoList = new ArrayList<>();
		dto = new TradeDto();
		dto.setAccount("test account");
		dto.setType("test type");
		dto.setBuyQuantity(25.0);
		idInteger = 10;
	}

	@After
	public void afterEachVerif() {
		verifyNoMoreInteractions(service);

	}

	@Test
	public void get_tradeList_successfully_200AndViewOk() throws Exception {
		dto.setId(idInteger);
		dtoList.add(dto);
		when(service.getAll()).thenReturn(dtoList);
		MvcResult result = mvc.perform(get("/trade/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/list"))
				.andReturn();
		verify(service, times(1)).getAll();
		assertTrue(result.getResponse().getContentAsString().contains("test"));
	}

	@Test
	public void get_addBidForm_successfully_200AndViewOk() throws Exception {
		mvc.perform(get("/trade/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"))
				.andExpect(model().attributeExists("tradeDto"));
	}

	@Test
	public void post_validate_successfully_dataValidatedAnd302() throws Exception {
		mvc.perform(post("/trade/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"))
				.andDo(print());
		verify(service, times(1)).create(any(TradeDto.class));
	}

	@Test
	public void get_tradeUpdate_succesfully_200AndViewOk() throws Exception {
		when(service.getById(idInteger)).thenReturn(dto);
		mvc.perform(get("/trade/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"))
				.andExpect(model().attributeExists("tradeDto"))
				.andExpect(content().string(containsString("test")));
		verify(service, times(1)).getById(idInteger);
	}

	@Test
	public void post_tradeUpdate_successfully_dataValidAnd302() throws Exception {
		mvc.perform(post("/trade/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));
		verify(service, times(1)).updateById(anyInt(), any(TradeDto.class));
	}

	@Test
	public void get_tradeDelete_successfully_dataValidAnd302() throws Exception {
		mvc.perform(get("/trade/delete/{id}", idInteger).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));
		verify(service, times(1)).delete(idInteger);
	}

	// -------------------------------------------------------------------------------------
	// ----- Evaluate the @valid annotation in controllers,
	// ----- request is send with invalid dto according the dto
	// ----- fields annotation. (@Min, @NotBlank, @NotNull ...)

	@Test
	public void post_tradeValidate_invalidDto_modelHasError() throws Exception {
		dto.setAccount(" ");
		dto.setBuyQuantity(null);
		dto.setType("");
		mvc.perform(post("/trade/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model()
						.attributeHasFieldErrors("tradeDto", "account", "type", "buyQuantity"))
				.andExpect(view().name("trade/add"));
	}

	@Test
	public void post_tradeUpdate_invalidDto_modelHasError() throws Exception {
		dto.setAccount("");
		dto.setBuyQuantity(-1.0);
		dto.setType(null);
		mvc.perform(post("/trade/update/{id}", idInteger).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(
						model().attributeHasFieldErrors("tradeDto",
								"account",
								"type",
								"buyQuantity"))
				.andExpect(view().name("trade/update"));
	}
	// --------------------------------------------------------------------------------
	// ----- Evaluate security filter,
	// ----- request is send with unauthenticated user

	@Test
	@WithAnonymousUser
	public void getHome_anonymousUser_401() throws Exception {
		mvc.perform(get("/trade/list")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_addBidForm_anonymousUser_401() throws Exception {
		mvc.perform(get("/trade/add")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_validate_anonymousUser_401() throws Exception {
		mvc.perform(post("/trade/validate").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_tradeUpdate_anonymousUser_401() throws Exception {
		mvc.perform(get("/trade/update")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_tradeUpdate_anonymousUser_then401() throws Exception {
		mvc.perform(post("/trade/update").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_tradedelete_anonymousUser_then401() throws Exception {
		mvc.perform(post("/trade/delete").with(csrf())).andExpect(status().isUnauthorized());
	}

}

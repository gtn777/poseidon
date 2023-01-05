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

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.utilities.ObjectToMultivalueMap;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BidListController.class)
@AutoConfigureMockMvc(secure = true)
public class BidListControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BidListService bidListService;

	@AfterEach
	private void afterEachVerif() {
		verifyNoMoreInteractions(bidListService);
	}

	@Test
	public void get_bidListList_successfully_200AndViewOk() throws Exception {
		List<BidListDto> dto = new ArrayList<>();
		dto.add(new BidListDto(1, "accountTest1", "home", (double) 10));
		when(bidListService.getAll()).thenReturn(dto);
		MvcResult result = mvc.perform(get("/bidList/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/list"))
				.andReturn();
		verify(bidListService, times(1)).getAll();
		assertTrue(result.getResponse().getContentAsString().contains("accountTest1"));
	}

	@Test
	public void get_addBidForm_successfully_200AndViewOk() throws Exception {
		mvc.perform(get("/bidList/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"))
				.andExpect(model().attributeExists("bidListDto"));
	}

	@Test
	public void post_validate_successfully_dataValidatedAnd302() throws Exception {
		BidListDto dto = new BidListDto("accountTest", "validate", 15.0);
		mvc.perform(post("/bidList/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bidList/list"))
				.andDo(print());
		verify(bidListService, times(1)).create(any(BidListDto.class));
	}

	@Test
	public void get_bidListUpdate_succesfully_200AndViewOk() throws Exception {
		int id = 10;
		BidListDto dto = new BidListDto("accountTest", "update", 15.0);
		when(bidListService.getById(id)).thenReturn(dto);
		mvc.perform(get("/bidList/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"))
				.andExpect(model().attributeExists("bidListDto"))
				.andExpect(content().string(containsString("accountTest")));
		verify(bidListService, times(1)).getById(id);
	}

	@Test
	public void post_bidListUpdate_successfully_dataValidAnd302() throws Exception {
		int id = 10;
		BidListDto dto = new BidListDto("accountTest", "update", 15.0);
		mvc.perform(post("/bidList/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bidList/list"));
		verify(bidListService, times(1)).updateById(anyInt(), any(BidListDto.class));
	}

	@Test
	public void get_bidListDelete_successfully_dataValidAnd302() throws Exception {
		int id = 10;

		mvc.perform(get("/bidList/delete/{id}", id).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bidList/list"));
		verify(bidListService, times(1)).delete(id);
	}

	// -------------------------------------------------------------------------------------
	// ----- Evaluate the @valid annotation in controllers,
	// ----- request is send with invalid dto according the dto
	// ----- fields annotation. (@Min, @NotBlank, @NotNull ...)

	@Test
	public void postValidate_invalidDto_modelHasError() throws Exception {
		BidListDto dto = new BidListDto(" ", "validateTest", 15.0);
		mvc.perform(post("/bidList/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("bidList/add"));
	}

	@Test
	public void post_bidListUpdate_invalidDto_modelHasError() throws Exception {
		int id = 10;
		BidListDto dto = new BidListDto(" ", "updateTest", 15.0);
		mvc.perform(post("/bidList/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("bidListDto", "account"))
				.andExpect(view().name("bidList/update"));
	}
	// --------------------------------------------------------------------------------
	// ----- Evaluate security filter,
	// ----- request is send with unauthenticated user

	@Test
	@WithAnonymousUser
	public void getHome_anonymousUser_401() throws Exception {
		mvc.perform(get("/bidList/list")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_addBidForm_anonymousUser_401() throws Exception {
		mvc.perform(get("/bidList/add")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_validate_anonymousUser_401() throws Exception {
		mvc.perform(post("/bidList/validate").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_bidListUpdate_anonymousUser_401() throws Exception {
		mvc.perform(get("/bidList/update")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_bidListUpdate_anonymousUser_then401() throws Exception {
		mvc.perform(post("/bidList/update").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_bidListdelete_anonymousUser_then401() throws Exception {
		mvc.perform(post("/bidList/delete").with(csrf())).andExpect(status().isUnauthorized());
	}

}

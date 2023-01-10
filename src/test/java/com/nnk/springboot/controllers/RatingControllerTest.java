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

import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.utilities.ObjectToMultivalueMap;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RatingController.class)
@AutoConfigureMockMvc(secure = true)
public class RatingControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RatingService ratingService;

	@AfterEach
	private void afterEachVerif() {
		verifyNoMoreInteractions(ratingService);
	}

	@Test
	public void get_ratingList_successfully_200AndViewOk() throws Exception {
		List<RatingDto> dto = new ArrayList<>();
		dto.add(new RatingDto(1, "rattingTest1", "sandPRating", "home", 10));
		when(ratingService.getAll()).thenReturn(dto);
		MvcResult result = mvc.perform(get("/rating/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/list"))
				.andReturn();
		verify(ratingService, times(1)).getAll();
		assertTrue(result.getResponse().getContentAsString().contains("rattingTest1"));
	}

	@Test
	public void get_addBidForm_successfully_200AndViewOk() throws Exception {
		mvc.perform(get("/rating/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/add"))
				.andExpect(model().attributeExists("ratingDto"));
	}

	@Test
	public void post_validate_successfully_dataValidatedAnd302() throws Exception {
		RatingDto dto = new RatingDto("validate", "accountTest", "validate", 15);
		mvc.perform(post("/rating/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rating/list"))
				.andDo(print());
		verify(ratingService, times(1)).create(any(RatingDto.class));
	}

	@Test
	public void get_ratingUpdate_succesfully_200AndViewOk() throws Exception {
		int id = 10;
		RatingDto dto = new RatingDto("update", "accountTest", "update", 15);
		when(ratingService.getById(id)).thenReturn(dto);
		mvc.perform(get("/rating/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/update"))
				.andExpect(model().attributeExists("ratingDto"))
				.andExpect(content().string(containsString("accountTest")));
		verify(ratingService, times(1)).getById(id);
	}

	@Test
	public void post_ratingUpdate_successfully_dataValidAnd302() throws Exception {
		int id = 10;
		RatingDto dto = new RatingDto("rating", "account", "fitchrating", 15);
		mvc.perform(post("/rating/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rating/list"));
		verify(ratingService, times(1)).updateById(anyInt(), any(RatingDto.class));
	}

	@Test
	public void get_ratingDelete_successfully_dataValidAnd302() throws Exception {
		int id = 10;

		mvc.perform(get("/rating/delete/{id}", id).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rating/list"));
		verify(ratingService, times(1)).delete(id);
	}

	// -------------------------------------------------------------------------------------
	// ----- Evaluate the @valid annotation in controllers,
	// ----- request is send with invalid dto according the dto
	// ----- fields annotation. (@Min, @NotBlank, @NotNull ...)

	@Test
	public void post_validate_invalidDto_modelHasError() throws Exception {
		RatingDto dto = new RatingDto(" ", "validateTest", "validateTest", 15);
		mvc.perform(post("/rating/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("ratingDto", "moodysRating"))
				.andExpect(view().name("rating/add"));
	}

	@Test
	public void post_ratingUpdate_invalidDto_modelHasError() throws Exception {
		int id = 10;
		RatingDto dto = new RatingDto("updateTest", " ", "updateTest", 15);
		mvc.perform(post("/rating/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("ratingDto", "sandPRating"))
				.andExpect(view().name("rating/update"));
	}
	// --------------------------------------------------------------------------------
	// ----- Evaluate security filter,
	// ----- request is send with unauthenticated user

	@Test
	@WithAnonymousUser
	public void getHome_anonymousUser_401() throws Exception {
		mvc.perform(get("/rating/list")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_addBidForm_anonymousUser_401() throws Exception {
		mvc.perform(get("/rating/add")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_validate_anonymousUser_401() throws Exception {
		mvc.perform(post("/rating/validate").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_ratingUpdate_anonymousUser_401() throws Exception {
		mvc.perform(get("/rating/update")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_ratingUpdate_anonymousUser_then401() throws Exception {
		mvc.perform(post("/rating/update").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_ratingdelete_anonymousUser_then401() throws Exception {
		mvc.perform(post("/rating/delete").with(csrf())).andExpect(status().isUnauthorized());
	}

}

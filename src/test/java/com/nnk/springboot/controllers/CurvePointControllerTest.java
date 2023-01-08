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

import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.utilities.ObjectToMultivalueMap;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CurvePointController.class)
@AutoConfigureMockMvc(secure = true)
public class CurvePointControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CurvePointService service;

	@AfterEach
	private void afterEachVerif() {
		verifyNoMoreInteractions(service);
	}

	@Test
	public void get_curvePointList_successfully_200AndViewOk() throws Exception {
		List<CurvePointDto> dto = new ArrayList<>();
		dto.add(new CurvePointDto(1, 11, 111.0, 1111.0));
		when(service.getAll()).thenReturn(dto);
		MvcResult result = mvc.perform(get("/curvePoint/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/list"))
				.andReturn();
		verify(service, times(1)).getAll();
		assertTrue(result.getResponse().getContentAsString().contains("1111.0"));
	}

	@Test
	public void get_addBidForm_successfully_200AndViewOk() throws Exception {
		mvc.perform(get("/curvePoint/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add"))
				.andExpect(model().attributeExists("curvePointDto"));
	}

	@Test
	public void post_validate_successfully_dataValidatedAnd302() throws Exception {
		CurvePointDto dto = new CurvePointDto(11, 111.0, 1111.0);
		mvc.perform(post("/curvePoint/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvePoint/list"))
				.andDo(print());
		verify(service, times(1)).create(any(CurvePointDto.class));
	}

	@Test
	public void get_curvePointUpdate_succesfully_200AndViewOk() throws Exception {
		int id = 10;
		CurvePointDto dto = new CurvePointDto(11, 111.0, 1111.0);
		when(service.getById(id)).thenReturn(dto);
		mvc.perform(get("/curvePoint/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"))
				.andExpect(model().attributeExists("curvePointDto"))
				.andExpect(content().string(containsString("111.0")));
		verify(service, times(1)).getById(id);
	}

	@Test
	public void post_curvePointUpdate_successfully_dataValidAnd302() throws Exception {
		int id = 10;
		CurvePointDto dto = new CurvePointDto(11, 111.0, 1111.0);
		mvc.perform(post("/curvePoint/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvePoint/list"));
		verify(service, times(1)).updateById(anyInt(), any(CurvePointDto.class));
	}

	@Test
	public void get_curvePointDelete_successfully_dataValidAnd302() throws Exception {
		int id = 10;

		mvc.perform(get("/curvePoint/delete/{id}", id).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvePoint/list"));
		verify(service, times(1)).delete(id);
	}

	// -------------------------------------------------------------------------------------
	// ----- Evaluate the @valid annotation in controllers,
	// ----- request is send with invalid dto according the dto
	// ----- fields annotation. (@Min, @NotBlank, @NotNull ...)

	@Test
	public void postValidate_invalidDto_modelHasError() throws Exception {
		CurvePointDto dto = new CurvePointDto(11, -111.0, 1111.0);
		mvc.perform(post("/curvePoint/validate").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("curvePointDto", "term"))
				.andExpect(view().name("curvePoint/add"));
	}

	@Test
	public void post_curvePointUpdate_invalidDto_modelHasError() throws Exception {
		int id = 10;
		CurvePointDto dto = new CurvePointDto(null, 111.0, 0.0);
		mvc.perform(post("/curvePoint/update/{id}", id).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.params(ObjectToMultivalueMap.convert(dto)))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("curvePointDto", "value"))
				.andExpect(model().attributeHasFieldErrors("curvePointDto", "curveId"))
				.andExpect(view().name("curvePoint/update"));
	}
	// --------------------------------------------------------------------------------
	// ----- Evaluate security filter,
	// ----- request is send with unauthenticated user

	@Test
	@WithAnonymousUser
	public void getHome_anonymousUser_401() throws Exception {
		mvc.perform(get("/curvePoint/list")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_addBidForm_anonymousUser_401() throws Exception {
		mvc.perform(get("/curvePoint/add")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_validate_anonymousUser_401() throws Exception {
		mvc.perform(post("/curvePoint/validate").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_curvePointUpdate_anonymousUser_401() throws Exception {
		mvc.perform(get("/curvePoint/update")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void post_curvePointUpdate_anonymousUser_then401() throws Exception {
		mvc.perform(post("/curvePoint/update").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithAnonymousUser
	public void get_curvePointdelete_anonymousUser_then401() throws Exception {
		mvc.perform(post("/curvePoint/delete").with(csrf())).andExpect(status().isUnauthorized());
	}

}

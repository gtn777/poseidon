package com.nnk.springboot.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.services.BidListService;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class BidListControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BidListService bidListService;

	private final String BASE_URL = "http://localhost/";
	private final String URL_FOR_REDIRECT_AFTER_401_UNAUTHORIZED = BASE_URL + "login";
	private final String URL_FOR_REDIRECT_AFTER_VALIDATE = "/bidList/list";

	@Test
	@WithAnonymousUser
	public void getHome_unauthenticated__redirectToLogin() throws Exception {
		mvc.perform(get("/bidList/list"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(URL_FOR_REDIRECT_AFTER_401_UNAUTHORIZED));
		verifyNoMoreInteractions(bidListService);
	}
	
	@Test
	@WithMockUser
	public void getHome_authenticated_status200() throws Exception {

		List<BidListDto> bidListDtoList = new ArrayList<>();
		bidListDtoList.add(new BidListDto(1, "accountTest1", "typeTest1", (double) 10));
		bidListDtoList.add(new BidListDto(12, "accountTest12", "typeTest12", (double) 120));
		bidListDtoList.add(new BidListDto(5, "accountTest5", "typeTest5", (double) 50));

		when(bidListService.getAll()).thenReturn(bidListDtoList);

		MvcResult result = mvc.perform(get("/bidList/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/list"))
				.andReturn();
		verify(bidListService, times(1)).getAll();
		verifyNoMoreInteractions(bidListService);
		assertThat(result.getResponse().getContentAsString().contains("accountTest12"));
	}

	@Test
	@WithAnonymousUser
	public void getAddBidForm_unauthenticated_redirectToLogin() throws Exception {
		mvc.perform(get("/bidList/add"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(URL_FOR_REDIRECT_AFTER_401_UNAUTHORIZED));
		verifyNoMoreInteractions(bidListService);
	}

	@Test
	@WithMockUser
	public void getAddBidForm_withAuthenticatedUser_status200WithView() throws Exception {
		mvc.perform(get("/bidList/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));
		verifyNoMoreInteractions(bidListService);
	}

	@Test
	@WithAnonymousUser
	public void postValidate_unauthenticated_redirectToLogin() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		BidListDto bidListDto = new BidListDto("accountTest", "typeTest", (double) 150);
		String json = mapper.writeValueAsString(bidListDto);

		mvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(json))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(URL_FOR_REDIRECT_AFTER_401_UNAUTHORIZED));
		verifyNoMoreInteractions(bidListService);
	}

	@Test
	@WithMockUser
	public void postValidate_thenRedirect() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		BidListDto bidListDto = new BidListDto("accountTest", "typeTest", (double) 150);
		String json = mapper.writeValueAsString(bidListDto);

		mvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(json))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(URL_FOR_REDIRECT_AFTER_VALIDATE));
		verify(bidListService).create(any(BidListDto.class));
		verifyNoMoreInteractions(bidListService);
	}

}

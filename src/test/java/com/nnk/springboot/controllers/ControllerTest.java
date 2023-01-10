package com.nnk.springboot.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.services.CurvePointService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

	@Mock
	private CurvePointService service;

	@Mock
	private Model model;

	@InjectMocks
	CurvePointController controller;
	
	private List<CurvePointDto> dto;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	public void verifyaaft() {
		verifyNoMoreInteractions(service, model);
	}

	@Test
	public void getList_must_returnString() {
		
		doReturn(dto).when(service).getAll();
		log.info("do Return when..  OK");
		Object resultObject = controller.getList(model);

		verify(model, times(1)).addAttribute("dto", dto);
		verify(service, times(1)).getAll();
		assertTrue(resultObject instanceof String);
		assertTrue(resultObject.toString().contentEquals("curvePoint/list"));

	}

}

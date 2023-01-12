package com.nnk.springboot.utilities;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ObjectToMultivalueMap {

	static public MultiValueMap<String, String> convert(Object obj) {
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		Map<String, String> map = new ObjectMapper().convertValue(obj,
				new TypeReference<Map<String, String>>() {
				});
		multiValueMap.setAll(map);
		return multiValueMap;
	}
}

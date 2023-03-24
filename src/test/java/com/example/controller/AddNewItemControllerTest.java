package com.example.controller;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.example.form.ItemForm;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@AutoConfigureMockMvc
public class AddNewItemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private BindingResult bindingResultMock;
	
	@Mock
	Model model;

	@Test
	@WithMockUser(username = "masa", password = "Shibata20")
	public void indexTest() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/add")).andExpect(status().isOk())
				.andReturn();
		System.out.println("Mvcresult : " + mvcResult);
		ModelAndView mav = mvcResult.getModelAndView();
		System.out.println("Mav : " + mav);
		ModelAndViewAssert.assertViewName(mav, "add");

	}
	
//	@Test
//	@WithMockUser(username = "masa", password = "Shibata20")
//    public void testSubmit_withErrors() throws Exception {
//        when(bindingResultMock.hasErrors()).thenReturn(true);
//        when(bindingResultMock.hasFieldErrors("category")).thenReturn(true);
//        model.addAttribute("categoryError", "categoryの選択は必須です");
//
//        mockMvc.perform(post("/add/submit")
//                .param("price", "1000")
//                .param("category", ""))
////                .flashAttr("itemForm", new ItemForm()))
//                .andExpect(status().isOk())
////                .andExpect(model().attributeExists("categoryError"))
//                .andExpect(model().attribute("categoryError", "categoryの選択は必須です"))
//                .andExpect(view().name("index"));
//
//        verify(bindingResultMock).hasErrors();
//        verify(bindingResultMock).hasFieldErrors("category");
//    }
}

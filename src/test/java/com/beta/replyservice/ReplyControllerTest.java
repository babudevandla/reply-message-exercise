package com.beta.replyservice;

import com.beta.replyservice.service.ReplyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ReplyControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ReplyController replyController;

    @Mock
    private ReplyService replyService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(replyController)
                .build();
    }
    @DisplayName("Test Default Reply message")
    @Test
    public void testDefaultReplyMessage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/reply").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new ReplyMessage("Message is empty")))).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @DisplayName("Test Custom Reply message")
    @Test
    public void testCustomReplyMessage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/reply/{message}","Helloworld").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new ReplyMessage("Helloworld")))).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @DisplayName("Test Valid format Rules Reply message : 11-kbzw9ru")
    @Test
    public void testValidFormatRuleReplyMessage() throws Exception {
        ReplyResponse replyResponse =  new ReplyResponse();
        replyResponse.setData("kbzw9ru");
        when(replyService.applyReplyingRules("11-kbzw9ru")).thenReturn(replyResponse);
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","11-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("kbzw9ru"));
    }

    @DisplayName("Test Valid format Rules2 for Reply Message : 12-kbzw9ru")
    @Test
    public void testValidformatRules2forReplyMessage() throws Exception {
        ReplyResponse replyResponse =  new ReplyResponse();
        replyResponse.setData("5a8973b3b1fafaeaadf10e195c6e1dd4");
        when(replyService.applyReplyingRules("12-kbzw9ru")).thenReturn(replyResponse);
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","12-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("5a8973b3b1fafaeaadf10e195c6e1dd4"));
    }

    @DisplayName("Test Valid format Rules3 for Reply Message : 12-kbzw9ru")
    @Test
    public void testValidformatRules3forReplyMessage() throws Exception {
        ReplyResponse replyResponse =  new ReplyResponse();
        replyResponse.setData("e8501e64cf0a9fa45e3c25aa9e77ffd5");
        when(replyService.applyReplyingRules("22-kbzw9ru")).thenReturn(replyResponse);
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","22-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("e8501e64cf0a9fa45e3c25aa9e77ffd5"));
    }

    @DisplayName("Test Valid format Rules4 for Reply Message : 21-kbzw9ru")
    @Test
    public void testValidformatRules4forReplyMessage() throws Exception {
        ReplyResponse replyResponse =  new ReplyResponse();
        replyResponse.setData("daf168567f92b1c464459087eaaefaf0");
        when(replyService.applyReplyingRules("21-kbzw9ru")).thenReturn(replyResponse);
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","21-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("daf168567f92b1c464459087eaaefaf0"));
    }


    @DisplayName("Test Invalid Input format for Reply message")
    @Test
    public void testInvalidInputFormatReplyMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","13-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input"));
    }

    @DisplayName("Test Invalid Rule format : 1-kbzw9ru ")
    @Test
    public void testInvalidRuleFormatReplyMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","1-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input. Rules contain two numbers {1 or 2}"));
    }

    @DisplayName("Test Invalid Rule format for message : 111-kbzw9ru")
    @Test
    public void testInvalidRuleFormat1ReplyMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","111-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input. Rules contain two numbers {1 or 2}"));
    }

    @DisplayName("Test Invalid Rule format for message : 113-kbzw9ru")
    @Test
    public void testInvalidRuleFormat2ReplyMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v2/reply/{message}","113-kbzw9ru")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input"));
    }
}

package com.beta.replyservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beta.replyservice.service.ReplyService;

@RestController
public class ReplyController {

	private Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	ReplyService replyService;

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}

	@GetMapping("/v2/reply/{message}")
	public ResponseEntity<?> replyingRules(@PathVariable String message) {
		logger.info("ReplyController -> replyingRules .. Start");
		if (message != null) {
			String[] input = message.split("-");
			boolean result = input[0].matches("^[1-2]+$");
			if (!result) {
				// error message response
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Invalid input");
				return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
			}
			if(input[0].length()==1 || input[0].length() > 2) {
				// error message response
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Invalid input. Rules contain two numbers {1 or 2}");
				return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
			}
		}
		ReplyResponse replyMessage = replyService.applyReplyingRules(message);
		return new ResponseEntity<ReplyResponse>(replyMessage, HttpStatus.OK);
	}
}
package com.beta.replyservice.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.beta.replyservice.ReplyResponse;

@Service
public class ReplyServiceImpl implements ReplyService {

	private Logger logger = LoggerFactory.getLogger(ReplyServiceImpl.class);

	@Override
	public ReplyResponse applyReplyingRules(String message) {
		String[] input = message.split("-");

		char key = input[0].charAt(0);
		String rulesMsg = applyingActualRules(key, input[1]);
		if (!rulesMsg.equalsIgnoreCase("default")) {
			rulesMsg = applyingActualRules(input[0].charAt(1), rulesMsg);
		}
		ReplyResponse replyMessage = new ReplyResponse();
		replyMessage.setData(rulesMsg);
		return replyMessage;

	}

	private String applyingActualRules(char key, String message) {
		logger.info("Rule Key : {}", key);
		logger.info("Message : {}", message);
		String rulesMsg = null;
		switch (key) {
		case '1':
			rulesMsg = reverseMessage(message);
			break;
		case '2':
			rulesMsg = HashMD5Message(message);
			break;
		default:
			rulesMsg = "default";
			break;
		}
		logger.info("After Rule applyed Message : {}", rulesMsg);
		return rulesMsg;
	}

	private String HashMD5Message(String message) {
		MessageDigest md;
		String hashtext = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(message.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);

			hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println(hashtext);
		return hashtext;
	}

	private String reverseMessage(String message) {
		String reversed = message.chars().mapToObj(c -> (char) c).reduce("", (s, c) -> c + s, (s1, s2) -> s2 + s1);
		return reversed;
	}

}

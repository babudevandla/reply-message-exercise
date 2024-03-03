package com.beta.replyservice.service;

import com.beta.replyservice.ReplyResponse;

public interface ReplyService {

	ReplyResponse applyReplyingRules(String message);

}

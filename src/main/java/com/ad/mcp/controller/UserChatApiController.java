/**
 * 
 */
package com.ad.mcp.controller;

/**
 * 
 */

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ad.mcp.mcp.TodoMcpTools;

@RestController
@RequestMapping("/api/chat")
public class UserChatApiController {

    private static final Logger log = LoggerFactory.getLogger(UserChatApiController.class);
    private final ChatClient chatClient;

    public UserChatApiController(ChatClient.Builder builder, com.ad.mcp.mcp.TodoMcpTools todoMcpTools) {
        this.chatClient = builder
                .defaultTools(todoMcpTools)
                .defaultSystem("""
                        You are a dedicated Task & Todo assistant.
                        You ONLY answer questions and perform operations related to the user's todo list.
                        If the user asks general knowledge questions, chit-chat, or unrelated topics,
                        politely refuse and remind them that you can only manage their todo items.
                        """)
                .build();
    }

    @PostMapping
    public ChatResponse handleUserMessage(@RequestBody ChatRequest request) {
        log.info("=== [INCOMING REQUEST] User Message: '{}' ===", request.message());

        String aiResponse = chatClient.prompt()
                .user(request.message())
                .call()
                .content();

        log.info("=== [OUTGOING RESPONSE] LLM Reply: '{}' ===", aiResponse);

        return new ChatResponse(aiResponse);
    }

    public record ChatRequest(String message) {}
    public record ChatResponse(String reply) {}
}
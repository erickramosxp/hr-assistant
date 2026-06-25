package br.com.devsuperior.hr_assistant.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import reactor.core.publisher.Flux;

public class PromptLoggingAdvisor implements BaseAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(PromptLoggingAdvisor.class);
    private final int order;

    public PromptLoggingAdvisor(int order) {
        this.order = order;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {

        logPrompt(request);
        Flux<ChatClientResponse> responses = chain.nextStream(request);

        return new ChatClientMessageAggregator()
                .aggregateChatClientResponse(responses, r -> logResponse(request, r));
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return order;
    }

    private void logPrompt(ChatClientRequest request) {

        if (!logger.isDebugEnabled()) return;

        Object conversationId = request.context().get(ChatMemory.CONVERSATION_ID);
        StringBuilder prompt = new StringBuilder();

        for (Message m: request.prompt().getInstructions()) {
            prompt.append("\n┌── ").append(m.getMessageType()).append(" ──\n").append(m.getText());
        }
        logger.debug("📤 IDA — Prompt enviado ao modelo [conversationId={}]{}", conversationId, prompt);
    }

    private void logResponse(ChatClientRequest request, ChatClientResponse response) {

        if (!logger.isDebugEnabled()) return;

        Object conversationId = request.context().get(ChatMemory.CONVERSATION_ID);

        String answer = response.chatResponse().getResult().getOutput().getText();
        logger.debug("📥 VOLTA — Resposta gerada pelo modelo [conversationId={}]\n{}", conversationId, answer);
    }
}

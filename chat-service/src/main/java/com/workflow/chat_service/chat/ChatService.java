package com.workflow.chat_service.chat;

public interface ChatService {

    ChatDTO createChat(Long projectId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;
}

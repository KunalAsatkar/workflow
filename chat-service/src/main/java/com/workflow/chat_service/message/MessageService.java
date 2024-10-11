package com.workflow.chat_service.message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Message message, Long projectId) throws Exception;

    List<Message> getMessagesByProjectId(Long projectId) throws Exception;


}

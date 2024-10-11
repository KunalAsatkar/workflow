package com.workflow.chat_service.message;

import com.workflow.chat_service.chat.Chat;
import com.workflow.chat_service.chat.ChatServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatServiceImpl chatService;

    public MessageServiceImpl(MessageRepository messageRepository, ChatServiceImpl chatService) {
        this.chatService = chatService;
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(Message message, Long projectId) throws Exception {
        // call chat-service to get chatId
        Chat chat = chatService.getChatByProjectId(projectId);
        Message newMessage = Message.builder()
                .senderId(message.getSenderId())
                .content(message.getContent())
                .createdAt(LocalDateTime.now())
                .chat(chat)
                .build();
        return messageRepository.save(newMessage);
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        try{
            Chat chat = chatService.getChatByProjectId(projectId);
            if (chat == null) {
                throw new Exception("chat not found");
            }
            return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}

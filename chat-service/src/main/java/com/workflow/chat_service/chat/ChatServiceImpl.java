package com.workflow.chat_service.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public ChatDTO createChat(Long projectId) throws Exception {
        try{

        Chat chat = Chat.builder()
                .projectId(projectId)
                .build();
        Chat savedChat = chatRepository.save(chat);
        return ChatDTO.builder()
                .id(savedChat.getId())
                .projectId(savedChat.getProjectId())
                .build();
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Chat getChatByProjectId(Long projectId) throws Exception {
        try{
            Chat chat = chatRepository.findByProjectId(projectId).orElse(null);
            if(chat == null){
                throw new Exception("chat not found");
            }
            return chat;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}

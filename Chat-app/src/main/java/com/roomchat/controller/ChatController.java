package com.roomchat.controller;

import com.roomchat.dto.Message;
import com.roomchat.dto.MessageRequest;
import com.roomchat.dto.Room;
import com.roomchat.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Controller
@CrossOrigin("*")
public class ChatController {

    @Autowired
    private RoomService roomService;

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ) {

        Room room = roomService.findByRoomId(roomId);
        Message message = new Message();
        if (room != null) {
            message.setMessage(request.getContent());
            message.setSenderName(request.getSender());
            message.setTimeStamp(LocalDateTime.now());

//            get all the message form the room , add the new message and then save the message
            room.getMessages().add(message);
            roomService.saveRooom(room);
        } else {
            throw  new RuntimeException("No room found having roomId "+ roomId);
        }
        return message;
    }
}

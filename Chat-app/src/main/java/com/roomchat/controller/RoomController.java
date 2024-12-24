package com.roomchat.controller;

import com.roomchat.dto.Message;
import com.roomchat.service.RoomService;
import com.roomchat.dto.Room;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@RequestBody Room room){

//        check the room with room id
        if (roomService.findByRoomId(room.getRoomId()) != null){
            return ResponseEntity.badRequest().body("Room with id "+room.getRoomId() +" Already resent");
        }else{
            Room newRooom = new Room();
            newRooom.setId(room.getId());
            Room savedRoom = roomService.saveRooom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
        }
    }

    // get room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){
        Room room = roomService.findRoomById(roomId);
        if (room == null){
            return ResponseEntity.badRequest().body("The given room with id "+ roomId+" is not present");
        }
        return ResponseEntity.ok(room);
    }

    // get message of room
    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
            ){
        Room room = roomService.findByRoomId(roomId);
        if (room == null){
            return ResponseEntity.badRequest().body("The given room with id "+ roomId+" is not present");
        }
        List<Message> messages = room.getMessages();

        int start = Math.max(0,messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        List<Message> pageMessage = messages.subList(start, end);

        return ResponseEntity.ok(pageMessage);
    }
}

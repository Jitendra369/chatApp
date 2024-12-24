package com.roomchat.service;

import com.roomchat.dto.Room;

import java.util.List;

public interface RoomService {

    Room saveRooom (Room room);
    Room findRoomById(String id);
    List<Room> fetAllRooms();
    Room findByRoomId(String roomId);
}

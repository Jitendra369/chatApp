package com.roomchat.repo;

import com.roomchat.dto.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepo extends MongoRepository<Room, String> {

    Room findByRoomId(String roomId);
}

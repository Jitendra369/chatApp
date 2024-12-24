package com.roomchat.service;

import com.roomchat.repo.RoomRepo;
import com.roomchat.dto.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Override
    public Room saveRooom(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public Room findRoomById(String id) {
        Optional<Room> roomOptional = roomRepo.findById(id);
        if (roomOptional.isPresent()){
            return roomOptional.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Room> fetAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Room findByRoomId(String roomId) {
        return roomRepo.findByRoomId(roomId);
    }

}

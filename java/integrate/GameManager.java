package game;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

  private Map<Long, Room> rooms = new HashMap<>();

  public GameManager() {
  }

  public Response makeMove(Long roomId, Long playerId, Point point) {
    Room room = rooms.get(roomId);
    return room.makeMove(playerId, point);
  }

  public void addRoom(Room room) {
    rooms.put(1L, room);
  }

  public Map<Long, Room> getRooms() {
    return rooms;
  }

  public void setRooms(Map<Long, Room> rooms) {
    this.rooms = rooms;
  }
}

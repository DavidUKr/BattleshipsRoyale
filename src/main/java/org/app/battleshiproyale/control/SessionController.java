package org.app.battleshiproyale.control;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.app.battleshiproyale.model.Player;
import org.app.battleshiproyale.model.PlayerMapDTO;
import org.app.battleshiproyale.service.SessionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping(value = "/join")
    public ResponseEntity<Map<String, Object>> joinBattle(@RequestParam String player_id) {
        Map<String, Object> response = new HashMap<>();
        if (sessionService.joinPlayerToBattle(player_id)) {
            response.put("status", "Player accepted");
            response.put("message", "Waiting for another player...");
        } else {
            response.put("status", "Session full");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPlayersJoinedStatus() {
        Map<String, Object> response = new HashMap<>();
        if(sessionService.isGameReady()){
            response.put("playerIds", sessionService.getJoinedPlayerIds());
        }
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/join")
    public ResponseEntity<Boolean> resetSession() {
        sessionService.resetSession();
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/placeShips", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> placePlayerShips(@RequestParam String player_id, @RequestBody PlayerMapDTO playerMapDTO) {
        if (sessionService.placePlayerShips(player_id, playerMapDTO))
            return ResponseEntity.ok("Ships placed");
        else return ResponseEntity.ok("Wrong ID");
    }

    @GetMapping(value = "/ready", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Player>> getPlayersReadyStatus() {
        return ResponseEntity.ok(sessionService.getPlayersReadyStatus());
    }
}
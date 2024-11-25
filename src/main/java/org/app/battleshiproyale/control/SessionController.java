package org.app.battleshiproyale.control;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping(value = "/join/{player_id}")
    public ResponseEntity<Map<String, Object>> joinBattle(@PathVariable String player_id) {
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

    @PostMapping(value = "/placeShips/{player_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> placePlayerShips(@PathVariable String player_id, @RequestBody PlayerMapDTO playerMapDTO) {
        sessionService.placePlayerShips(player_id, playerMapDTO);
        return ResponseEntity.ok("Ships placed");
    }

    @GetMapping(value = "/ready")
    public ResponseEntity<Boolean> getPlayersReadyStatus() {
        return ResponseEntity.ok(sessionService.getPlayersReadyStatus());
    }
}
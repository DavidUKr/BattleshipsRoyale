package org.app.battleshiproyale.control;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.model.PlayerMapDTO;
import org.app.battleshiproyale.service.SessionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping(value = "/join/{player_id}")
    public ResponseEntity<String> joinBattle(@PathVariable String player_id) {
        if (sessionService.joinPlayerToBattle(player_id)){
            return ResponseEntity.ok("Player accepted");
        }
            return ResponseEntity.ok("Session full");
    }

    @GetMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPlayersJoinedStatus() {
        return ResponseEntity.ok(sessionService.getPlayersJoinedStatus());
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
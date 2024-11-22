package org.app.battleshiproyale.control_multi;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.model.GameStateDTO;
import org.app.battleshiproyale.model.HitResultDTO;
import org.app.battleshiproyale.service.GameService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "BattleshipRoyale/api/v2/games")
@RequiredArgsConstructor
public class GameController {
//
//    private final GameService gameService;
//
//    @PostMapping(value = "/generate/{session_id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<GameStateDTO> generateGame(@PathVariable String session_id) {
//        GameStateDTO gameState = gameService.generateGame(session_id);
//        return ResponseEntity.ok(gameState);
//    }
//
//    @PostMapping(value = "/hit/{session_id}/{player_id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HitResultDTO> hit(@PathVariable String session_id, @PathVariable String player_id) {
//        HitResultDTO hitResult = gameService.hit(session_id, player_id);
//        return ResponseEntity.ok(hitResult);
//    }
//
//    @GetMapping(value = "/state/{session_id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<GameStateDTO> getGameState(@PathVariable String session_id) {
//        GameStateDTO gameState = gameService.getGameState(session_id);
//        return ResponseEntity.ok(gameState);
//    }
}
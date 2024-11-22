package org.app.battleshiproyale.control;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.model.GameStateDTO;
import org.app.battleshiproyale.model.HitDTO;
import org.app.battleshiproyale.model.HitResultDTO;
import org.app.battleshiproyale.model.PerkDTO;
import org.app.battleshiproyale.service.GameService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping(value = "/hit/{player_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HitResultDTO> hit(@PathVariable String player_id, @RequestBody HitDTO hitDTO) {
        HitResultDTO hitResult = gameService.hit(player_id, hitDTO);
        return ResponseEntity.ok(hitResult);
    }

    @PostMapping(value = "/use_perk/{player_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> usePerk(@PathVariable String player_id, @RequestBody PerkDTO perkDTO) {
        boolean approved = gameService.usePerk(player_id, perkDTO);
        return ResponseEntity.ok(approved);
    }

    @GetMapping(value = "/stamina/{player_id}")
    public ResponseEntity<Integer> getPLayerStamina(@PathVariable String player_id) {
        int stamina=gameService.getPlayerStamina(player_id);
        return ResponseEntity.ok(stamina);
    }

    @GetMapping(value = "/state/{player_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStateDTO> getGameState(@PathVariable String player_id) {
        GameStateDTO gameState = gameService.getGameState(player_id);
        return ResponseEntity.ok(gameState);
    }
}

package org.app.battleshiproyale.control_multi;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.service.SessionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "BattleshipRoyale/api/v2/sessions")
@RequiredArgsConstructor
public class SessionController {

//    private final SessionService sessionService;
//
//    @PostMapping(value = "/create/{player_id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public SessionDTO createSession(@PathVariable String player_id) {
//        return sessionService.createSession(player_id);
//    }
//
//    @PostMapping(value = "/join/{sessionId}/{player_id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public SessionDTO joinSessionById(@PathVariable Long sessionId, @PathVariable String player_id) {
//        return sessionService.joinSessionById(sessionId, player_id);
//    }
//
//    @GetMapping(value = "/free", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<SessionDTO> getFreeSessions() {
//        return sessionService.getFreeSessions();
//    }
//
//    @DeleteMapping(value="/{session_id}")
//    public ResponseEntity<String> endSession(@PathVariable String session_id) {
//        sessionService.endSession(session_id);
//        return ResponseEntity.ok("Session ended");
//    }
}
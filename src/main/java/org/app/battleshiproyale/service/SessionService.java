package org.app.battleshiproyale.service;

import org.app.battleshiproyale.model.SessionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    public SessionDTO createSession(String player_id) {
        return new SessionDTO();
    }

    public SessionDTO joinSessionById(Long sessionId, String player_id) {
        return new SessionDTO();
    }

    public List<SessionDTO> getFreeSessions() {
        return null;
    }

    public void endSession(String sessionId) {
        
    }
}

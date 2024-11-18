package org.app.battleshiproyale.control;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.model.StatsDTO;
import org.app.battleshiproyale.service.GlobalService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "BattleshipRoyale/api/v1/global")
@RequiredArgsConstructor
public class GlobalController {

    private final GlobalService globalService;

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatsDTO getGlobalStats(){
        return globalService.getGlobalStats();
    }

}

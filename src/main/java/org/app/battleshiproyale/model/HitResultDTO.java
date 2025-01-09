package org.app.battleshiproyale.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class HitResultDTO {
    private Integer hitResult;//0 or 4
    private String message;
}

package nl.fhict.websocket.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Point {
    @Getter
    private int x;
    @Getter
    private int y;
}

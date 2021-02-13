package UI;

import java.awt.Color;

public enum Colors {
    north(new Color(105, 139, 255)),
    south(new Color(255, 138, 105)),
    castle(new Color(74, 55, 55)),
    castle2(new Color(224, 225, 192)),
    field(new Color(237, 235, 227)),
    blockade(new Color(126, 13, 24));
    public Color value;

    Colors(Color c) {
        this.value = c;
    }
}
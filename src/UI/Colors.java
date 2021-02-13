package UI;

import java.awt.Color;

public enum Colors {
    north(new Color(105, 239, 255)),
    south(new Color(255, 138, 105));

    public Color value;

    Colors(Color c) {
        this.value = c;
    }
}
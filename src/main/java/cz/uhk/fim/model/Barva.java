package cz.uhk.fim.model;

import java.awt.*;

public enum Barva {
    red, green, blue;

    public Color color()
    {
        if (this.equals(Barva.red)) return Color.red;
        if (this.equals(Barva.green)) return Color.green;
        if (this.equals(Barva.blue)) return Color.blue;

        return Color.white;
    }

}

package tools;

import java.awt.*;

/**
 * Created by tetrel on 03/02/16.
 */
public enum Colors {

    RADIAL_VELOCITY("Radial Velocity", new Color(255,0,0)),
    TRANSIT("Transit",new Color(0,255,0)),
    IMAGING("Imaging", new Color(0,0,255)),
    ASTROMETRY("Astrometry", new Color(0,0,0)),
    ECLIPSE_TIMING_VARIATIONS("Eclipse Timing Variations",new Color(255,0,255)),
    ORBITAL_BRIGHTNESS_MODULATION("Orbital Brightness Modulation",new Color(0,150,0)),
    TRANSIT_TIMING_VARIATIONS("Transit Timing Variations",new Color(0,75,0)),
    MICROLENSING("Microlensing",new Color(102,0,204)),
    PULSAR_TIMING("Pulsar Timing",new Color(230,50,100)),
    PULSATION_TIMING_VARIATIONS("Pulsation Timing Variations", new Color(50,230,100));



    private String name;
    private Color color;

    private Colors (String name, Color color) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

}

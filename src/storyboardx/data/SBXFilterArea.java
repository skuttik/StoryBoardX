/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.data;

/**
 *
 * @author skuttik
 */
public class SBXFilterArea {

    private final SBXGeoPosition center;
    private final long radius;

    public SBXFilterArea(SBXGeoPosition center, long radius) {
        this.center = center;
        this.radius = radius;
    }

    public SBXGeoPosition getCenter() {
        return center;
    }

    public long getRadius() {
        return radius;
    }

}

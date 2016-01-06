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
public class SBXGeoPosition {
    private final Double latitude;     // [radians]
    private final Double longitude;   // [radians]

    public SBXGeoPosition(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    
    
}

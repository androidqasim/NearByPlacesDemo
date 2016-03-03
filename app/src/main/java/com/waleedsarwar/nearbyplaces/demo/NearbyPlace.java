package com.waleedsarwar.nearbyplaces.demo;

/**
 * @author Waleed Sarwar
 * @since February 23, 2016 03:53 PM
 */
public class NearbyPlace {
    private String name;
    private float likelihood;
    private String address;

    public NearbyPlace() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(float likelihood) {
        this.likelihood = likelihood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

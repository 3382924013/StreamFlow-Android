package com.streamflow.model;

import java.util.List;

public class VipPlan {
    private String id;
    private String name;
    private int price;
    private int originalPrice;
    private String duration;
    private List<String> features;
    private boolean recommended;

    public VipPlan(String id, String name, int price, int originalPrice,
                   String duration, List<String> features, boolean recommended) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.originalPrice = originalPrice;
        this.duration = duration;
        this.features = features;
        this.recommended = recommended;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getOriginalPrice() { return originalPrice; }
    public String getDuration() { return duration; }
    public List<String> getFeatures() { return features; }
    public boolean isRecommended() { return recommended; }
}

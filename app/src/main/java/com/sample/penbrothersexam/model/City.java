
package com.sample.penbrothersexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @Expose
    private String banner;
    @Expose
    private String color;
    @SerializedName("country_code")
    private String countryCode;
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private Long population;
    @Expose
    private String subtitle;
    @Expose
    private String type;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getColor() {
        return color;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPopulation() {
        return population;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getType() {
        return type;
    }


}

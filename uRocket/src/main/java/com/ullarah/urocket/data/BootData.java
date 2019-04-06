package com.ullarah.urocket.data;

import com.ullarah.urocket.init.RocketEnhancement;
import com.ullarah.urocket.init.RocketVariant;

public class BootData {

    private Integer power;
    private RocketVariant.Variant variant;
    private RocketEnhancement.Enhancement enhancement;

    public BootData(Integer power, RocketVariant.Variant variant, RocketEnhancement.Enhancement enhancement) {
        this.power = power;
        this.variant = variant;
        this.enhancement = enhancement;
    }

    public Integer getPower() {
        return power;
    }

    public RocketVariant.Variant getVariant() {
        return variant;
    }

    public RocketEnhancement.Enhancement getEnhancement() {
        return enhancement;
    }
}

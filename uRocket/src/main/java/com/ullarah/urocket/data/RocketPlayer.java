package com.ullarah.urocket.data;

import org.bukkit.entity.Player;

public class RocketPlayer {

    private final Player player;

    private boolean wearingJacket = false;
    private boolean usingBoots = false;
    private BootData bootData = null;

    private FlyLockout lockouts = new FlyLockout();
    private boolean effected = false;

    public RocketPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public FlyLockout getLockouts() {
        return lockouts;
    }

    public boolean isUsingBoots() {
        return usingBoots;
    }

    public void setUsingBoots(boolean usingBoots) {
        this.usingBoots = usingBoots;
    }

    public BootData getBootData() {
        return bootData;
    }

    public void setBootData(BootData bootData) {
        this.bootData = bootData;
    }

    public boolean isWearingJacket() {
        return wearingJacket;
    }

    public void setWearingJacket(boolean wearingJacket) {
        this.wearingJacket = wearingJacket;
    }

    public boolean isEffected() {
        return effected;
    }

    public void setEffected(boolean effected) {
        this.effected = effected;
    }
}

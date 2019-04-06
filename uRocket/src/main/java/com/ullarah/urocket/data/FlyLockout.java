package com.ullarah.urocket.data;

public class FlyLockout {

    public enum Sprint {
        NONE,
        AIR,
        LAND
    }

    private boolean inFuelLockout;
    private boolean inWater;
    private boolean inNoFlyZone;
    private Sprint sprintLock;

    public FlyLockout() {
        clear();
    }

    public void clear() {
        inFuelLockout = false;
        inWater = false;
        inNoFlyZone = false;
        sprintLock = Sprint.NONE;
    }

    public boolean isInLockout() {
        return inFuelLockout || inWater || inNoFlyZone || sprintLock != Sprint.NONE;
    }

    // -- Getters and Setters --

    public boolean isInFuelLockout() {
        return inFuelLockout;
    }

    public void setInFuelLockout(boolean inFuelLockout) {
        this.inFuelLockout = inFuelLockout;
    }

    public boolean isInWater() {
        return inWater;
    }

    public void setInWater(boolean inWater) {
        this.inWater = inWater;
    }

    public boolean isInNoFlyZone() {
        return inNoFlyZone;
    }

    public void setInNoFlyZone(boolean inNoFlyZone) {
        this.inNoFlyZone = inNoFlyZone;
    }

    public Sprint getSprintLock() {
        return sprintLock;
    }

    public void setSprintLock(Sprint sprintLock) {
        this.sprintLock = sprintLock;
    }
}

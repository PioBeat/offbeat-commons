package net.offbeatpioneer.android.components.pinpad;

import java.io.Serializable;

/**
 * @author Dominik Grzelak
 * @since 18.05.2017
 */

public class PinData implements Serializable {
    private String pinHash;
    private int pinLength;

    public PinData(String pinHash, int pinLength) {
        this.pinHash = pinHash;
        this.pinLength = pinLength;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public int getPinLength() {
        return pinLength;
    }

    public void setPinLength(int pinLength) {
        this.pinLength = pinLength;
    }
}

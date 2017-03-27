package engine;

import javafx.scene.input.KeyCode;

/**
 * Created by Fox Kiester on 3/15/17.
 * Contains keyboard settings.
 */
public class Settings {
    private static KeyCode keyC;
    private static KeyCode keyCSharp;
    private static KeyCode keyD;
    private static KeyCode keyDSharp;
    private static KeyCode keyE;
    private static KeyCode keyF;
    private static KeyCode keyFSharp;
    private static KeyCode keyG;
    private static KeyCode keyGSharp;
    private static KeyCode keyA;
    private static KeyCode keyASharp;
    private static KeyCode keyB;

    static {
        resetKeys();
    }

    private Settings() {

    }

    public static void resetKeys() {
        keyC = KeyCode.A;
        keyD = KeyCode.S;
        keyE = KeyCode.D;
        keyF = KeyCode.F;
        keyG = KeyCode.G;
        keyA = KeyCode.H;
        keyB = KeyCode.J;

        keyCSharp = KeyCode.W;
        keyDSharp = KeyCode.E;
        keyFSharp = KeyCode.T;
        keyGSharp = KeyCode.Y;
        keyASharp = KeyCode.U;
    }

    public static KeyCode getKeyC() {
        return keyC;
    }

    public static void setKeyC(KeyCode keyC) {
        Settings.keyC = keyC;
    }

    public static KeyCode getKeyCSharp() {
        return keyCSharp;
    }

    public static void setKeyCSharp(KeyCode keyCSharp) {
        Settings.keyCSharp = keyCSharp;
    }

    public static KeyCode getKeyD() {
        return keyD;
    }

    public static void setKeyD(KeyCode keyD) {
        Settings.keyD = keyD;
    }

    public static KeyCode getKeyDSharp() {
        return keyDSharp;
    }

    public static void setKeyDSharp(KeyCode keyDSharp) {
        Settings.keyDSharp = keyDSharp;
    }

    public static KeyCode getKeyE() {
        return keyE;
    }

    public static void setKeyE(KeyCode keyE) {
        Settings.keyE = keyE;
    }

    public static KeyCode getKeyF() {
        return keyF;
    }

    public static void setKeyF(KeyCode keyF) {
        Settings.keyF = keyF;
    }

    public static KeyCode getKeyFSharp() {
        return keyFSharp;
    }

    public static void setKeyFSharp(KeyCode keyFSharp) {
        Settings.keyFSharp = keyFSharp;
    }

    public static KeyCode getKeyG() {
        return keyG;
    }

    public static void setKeyG(KeyCode keyG) {
        Settings.keyG = keyG;
    }

    public static KeyCode getKeyGSharp() {
        return keyGSharp;
    }

    public static void setKeyGSharp(KeyCode keyGSharp) {
        Settings.keyGSharp = keyGSharp;
    }

    public static KeyCode getKeyA() {
        return keyA;
    }

    public static void setKeyA(KeyCode keyA) {
        Settings.keyA = keyA;
    }

    public static KeyCode getKeyASharp() {
        return keyASharp;
    }

    public static void setKeyASharp(KeyCode keyASharp) {
        Settings.keyASharp = keyASharp;
    }

    public static KeyCode getKeyB() {
        return keyB;
    }

    public static void setKeyB(KeyCode keyB) {
        Settings.keyB = keyB;
    }
}

package com.ullarah.ulib.function;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import java.util.HashMap;
import java.util.Map;

public class SignText {

    private static HashMap<String, Boolean> signFaceMap(final Location location) {

        HashMap<String, Boolean> signMap = new HashMap<>();

        for (String face : new String[]{"NORTH", "EAST", "SOUTH", "WEST"}) {

            switch (location.getBlock().getRelative(BlockFace.valueOf(face)).getType()) {

                case WALL_SIGN:
                    signMap.put(face, true);
                    break;

                case SIGN:
                    signMap.put(face, true);
                    break;

            }

        }

        return signMap;

    }

    public static void changeAll(final Location location, String[] text) {

        HashMap<String, Boolean> signMap = signFaceMap(location);

        for (Map.Entry<String, Boolean> sign : signMap.entrySet()) {

            if (sign.getValue()) {

                Sign signText = (Sign) location.getBlock().getRelative(BlockFace.valueOf(sign.getKey())).getState();

                for (int i = 0; i < text.length; ++i) signText.setLine(i, text[i]);

                signText.update();

            }

        }

    }

    public static void changeAllCheck(final Location location, int line, String extract, boolean cs, String[] text) {

        HashMap<String, Boolean> signMap = signFaceMap(location);

        for (Map.Entry<String, Boolean> sign : signMap.entrySet()) {

            if (sign.getValue()) {

                Sign signText = (Sign) location.getBlock().getRelative(BlockFace.valueOf(sign.getKey())).getState();

                if (!cs) extract = extract.toLowerCase();

                if (signText.getLine(0).equals(extract)) {

                    for (int i = 0; i < text.length; ++i) if (i != line) signText.setLine(i, text[i]);

                    signText.update();

                }

            }

        }

    }

    public static void changeLine(final Location location, Integer[] line, String[] text) {

        HashMap<String, Boolean> signMap = signFaceMap(location);

        for (Map.Entry<String, Boolean> sign : signMap.entrySet()) {

            if (sign.getValue()) {

                Sign signText = (Sign) location.getBlock().getRelative(BlockFace.valueOf(sign.getKey())).getState();

                for (int l : line) for (String s : text) signText.setLine(l, s);

                signText.update();

            }

        }

    }

    public static void clearLines(final Location location, Integer[] line) {

        HashMap<String, Boolean> signMap = signFaceMap(location);

        for (Map.Entry<String, Boolean> sign : signMap.entrySet()) {

            if (sign.getValue()) {

                Sign signText = (Sign) location.getBlock().getRelative(BlockFace.valueOf(sign.getKey())).getState();

                for (int l : line) signText.setLine(l, "");

                signText.update();

            }

        }

    }

    public static void clearAll(final Location location) {

        HashMap<String, Boolean> signMap = signFaceMap(location);

        for (Map.Entry<String, Boolean> sign : signMap.entrySet()) {

            if (sign.getValue()) {

                Sign signText = (Sign) location.getBlock().getRelative(BlockFace.valueOf(sign.getKey())).getState();

                signText.update();

                for (int l : new Integer[]{0, 1, 2, 3}) signText.setLine(l, "");

            }

        }

    }

}

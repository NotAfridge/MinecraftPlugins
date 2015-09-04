package com.ullarah.ulib.function;

public class RomanNumeralToInteger {

    private static int decodeSingle(char letter) {

        switch (letter) {

            case 'M':
                return 1000;
            case 'D':
                return 500;
            case 'C':
                return 100;
            case 'L':
                return 50;
            case 'X':
                return 10;
            case 'V':
                return 5;
            case 'I':
                return 1;

            default:
                return 0;

        }

    }

    public static int decode(String roman) {

        int result = 0;
        roman = roman.toUpperCase();

        for (int i = 0; i < roman.length() - 1; i++)
            if (decodeSingle(roman.charAt(i)) < decodeSingle(roman.charAt(i + 1)))
                result -= decodeSingle(roman.charAt(i));
            else result += decodeSingle(roman.charAt(i));

        result += decodeSingle(roman.charAt(roman.length() - 1));
        return result;

    }

}

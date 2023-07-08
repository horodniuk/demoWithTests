package com.example.demowithtests.util.validation;

import java.util.Locale;
import java.util.Set;

public class IsoCountryUtil {
    private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

    private IsoCountryUtil() {}

    public static boolean isValidISOCountry(String str) {
        return ISO_COUNTRIES.contains(str);
    }
}

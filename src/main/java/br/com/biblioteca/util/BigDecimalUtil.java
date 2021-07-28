package br.com.biblioteca.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalUtil {

    public static boolean isEqual(BigDecimal o1, BigDecimal o2) {
        if (isNull(o1) || isNull(o2)) return false;
        return o1.compareTo(o2) == 0;
    }

}

package com.iofairy.falcon.unit;

import com.iofairy.except.OutOfBoundsException;

import java.math.BigInteger;

/**
 * 字节单位相互转换的因子（系数）
 *
 * @since 0.4.10
 */
public class ByteFactor {
    public static final BigInteger X8 = BigInteger.valueOf(8);
    public static final BigInteger X1000 = BigInteger.valueOf(1000);
    public static final BigInteger X1024 = BigInteger.valueOf(1024);
    /*###################################################################################
     ************************************************************************************
     ------------------------------------------------------------------------------------
     *********************   各字节单位转成 byte 的系数（coefficient）   ********************
     ------------------------------------------------------------------------------------
     ************************************************************************************
     ###################################################################################*/
    public static final BigInteger B_COEF = X1024.pow(0);
    public static final BigInteger KIB_COEF = X1024.pow(1);
    public static final BigInteger MIB_COEF = X1024.pow(2);
    public static final BigInteger GIB_COEF = X1024.pow(3);
    public static final BigInteger TIB_COEF = X1024.pow(4);
    public static final BigInteger PIB_COEF = X1024.pow(5);
    public static final BigInteger EIB_COEF = X1024.pow(6);
    public static final BigInteger ZIB_COEF = X1024.pow(7);
    public static final BigInteger YIB_COEF = X1024.pow(8);
    public static final BigInteger KB_COEF = X1000.pow(1);
    public static final BigInteger MB_COEF = X1000.pow(2);
    public static final BigInteger GB_COEF = X1000.pow(3);
    public static final BigInteger TB_COEF = X1000.pow(4);
    public static final BigInteger PB_COEF = X1000.pow(5);
    public static final BigInteger EB_COEF = X1000.pow(6);
    public static final BigInteger ZB_COEF = X1000.pow(7);
    public static final BigInteger YB_COEF = X1000.pow(8);

    public static BigInteger getCoef(boolean isBinaryUnit, int index) {
        if (index < 0 || index > 8) throw new OutOfBoundsException(index, "The `index` can only be in the range [0, 8].");
        if (isBinaryUnit) {
            switch (index) {
                case 0:
                    return B_COEF;
                case 1:
                    return KIB_COEF;
                case 2:
                    return MIB_COEF;
                case 3:
                    return GIB_COEF;
                case 4:
                    return TIB_COEF;
                case 5:
                    return PIB_COEF;
                case 6:
                    return EIB_COEF;
                case 7:
                    return ZIB_COEF;
                default:
                    return YIB_COEF;
            }
        } else {
            switch (index) {
                case 0:
                    return B_COEF;
                case 1:
                    return KB_COEF;
                case 2:
                    return MB_COEF;
                case 3:
                    return GB_COEF;
                case 4:
                    return TB_COEF;
                case 5:
                    return PB_COEF;
                case 6:
                    return EB_COEF;
                case 7:
                    return ZB_COEF;
                default:
                    return YB_COEF;
            }
        }
    }

}

package com.example.menusensores_gl.orientation.utils;

import android.content.Context;


public class DisplayUtil {

    /**
     *
     * Convierta el valor px en valor dp, mantenga el tamaño sin cambios
     *
     * @param pxValue
     * El valor px a convertir
     * @return Conversión de valor dp completado
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        if (scale == 2.625) { // TODO: 2018/3/7 Compatible con los modelos OnePlus
            scale = (float) 3.0;
        }
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Convierta el valor dp en valor px, mantenga el tamaño sin cambios
     *
     * @param dpValue Valor dp a convertir
     * @return El valor de px convertido
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        if (scale == 2.625) { //
            scale = (float) 3.0;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Convierta el valor px en valor sp, mantenga el tamaño del texto sin cambios
     *
     * @param pxValue El valor px a convertir
     * @return Conversión de valor sp completado
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Convierta el valor sp en valor px, mantenga el tamaño del texto sin cambios
     *
     * @param spValue Valor de sp para convertir
     * @return El valor de px convertido
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
} 
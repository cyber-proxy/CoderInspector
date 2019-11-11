package org.android.developer.util;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author LC
 * @des
 */
public class LogUtil {
    public static void log(String log){
        Logger.getInstance("CoderInspector:::::::::::::::::::::").info(log);
        System.out.println(log);
    }

    public static void e(String log){
        Logger.getInstance("CoderInspector:::::::::::::::::::::").error(log);
        System.out.println(log);
    }

    public static void warm(String log){
        Logger.getInstance("CoderInspector:::::::::::::::::::::").warn(log);
        System.out.println(log);
    }
}

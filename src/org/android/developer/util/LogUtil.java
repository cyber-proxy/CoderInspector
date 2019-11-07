package org.android.developer.util;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author LC
 * @des
 */
public class LogUtil {
    public static void log(String log){
        Logger.getInstance("CoderInspector:::::::::::::::::::::").info(log);

    }
}

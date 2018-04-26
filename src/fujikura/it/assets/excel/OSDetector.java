/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.excel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Eton
 */
public class OSDetector {
    private static boolean isWindows = false;
    private static boolean isLinux = false;
    private static boolean isMac = false;

    static {
        String os = System.getProperty("os.name").toLowerCase();
        isWindows = os.contains("win");
        isLinux = os.contains("nux") || os.contains("nix");
        isMac = os.contains("mac");
    }

    public static boolean isWindows() {
        return isWindows;
    }

    public static boolean isLinux() {
        return isLinux;
    }

    public static boolean isMac() {
        return isMac;
    }

    public static boolean open(File file) {
        try {
            if (OSDetector.isWindows()) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
//            Runtime.getRuntime().exec(new String[]
//            {"rundll32 url.dll,FileProtocolHandler",
//             file.getAbsolutePath()});
                return true;
            } else if (OSDetector.isLinux() || OSDetector.isMac()) {
                Runtime.getRuntime().exec(new String[]{"xdg-open", file.getAbsolutePath()});
                return true;
            } else {
                // Unknown OS, try with desktop
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

}
package com.sinvon.traywords.utils;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.Native;

/**
 * 窗口工具类
 * @author sinvon
 * @since 2024年8月14日17:25:11
 */
public class WindowUtils {

    public static void setTopMost(WinDef.HWND hwnd) {
        // User32.INSTANCE.SetWindowPos(hwnd, User32.HWND_TOPMOST, 0, 0, 0, 0, WinDef.SWP_NOMOVE | WinDef.SWP_NOSIZE);
    }

    public static void removeTopMost(WinDef.HWND hwnd) {
        // User32.INSTANCE.SetWindowPos(hwnd, User32.HWND_NOTOPMOST, 0, 0, 0, 0, WinDef.SWP_NOMOVE | WinDef.SWP_NOSIZE);
    }
}

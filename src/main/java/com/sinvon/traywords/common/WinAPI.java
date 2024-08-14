package com.sinvon.traywords.common;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 * @description Win控制接口
 * @author sinvon
 * @since 2024年8月14日17:24:53
 */
public class WinAPI {

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        WinDef.HWND GetForegroundWindow();
        WinDef.LONG GetWindowLong(WinDef.HWND hWnd, int nIndex);
        WinDef.LONG SetWindowLong(WinDef.HWND hWnd, int nIndex, WinDef.LONG dwNewLong);
        boolean SetWindowPos(WinDef.HWND hWnd, WinDef.HWND hWndInsertAfter, int X, int Y, int cx, int cy, int uFlags);

        int GWL_EXSTYLE = -20;
        int WS_EX_TOPMOST = 0x00000008;
        int SWP_NOSIZE = 0x0001;
        int SWP_NOMOVE = 0x0002;
        int SWP_SHOWWINDOW = 0x0040;
        WinDef.HWND HWND_TOPMOST = new WinDef.HWND(Pointer.createConstant(-1));
    }

    public static void setTopMost(WinDef.HWND hwnd) {
        User32.INSTANCE.SetWindowLong(hwnd, User32.GWL_EXSTYLE, new WinDef.LONG(User32.INSTANCE.GetWindowLong(hwnd, User32.GWL_EXSTYLE).longValue() | User32.WS_EX_TOPMOST));
        User32.INSTANCE.SetWindowPos(hwnd, User32.HWND_TOPMOST, 0, 0, 0, 0, User32.SWP_NOSIZE | User32.SWP_NOMOVE | User32.SWP_SHOWWINDOW);
    }
}

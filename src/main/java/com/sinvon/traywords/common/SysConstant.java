package com.sinvon.traywords.common;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;

/**
 * 系统常量
 *
 * @author sinvon
 * @since 2024年8月14日17:21:42
 */
@Component
public class SysConstant {
    /**
     * 鼠标点击的坐标
     */
    public static Point initialClick;
    /**
     * 窗口隐藏的状态
     */
    public static boolean isHidden = false;
    /**
     * 窗口初始宽度
     */
    public static int originalWidth;
    /**
     * 窗口初始高度
     */
    public static int originalHeight;
    /**
     * 边缘检测的阈值 -  就是检测到鼠标靠近边缘的时候，触发隐藏窗口的阈值，单位：像素，值越大，距离屏幕边缘很远的时候就会自动吸附（收缩到屏幕边缘）
     */
    public static final int EDGE_THRESHOLD = 10;
    /**
     * 窗口隐藏时的厚度 - 就是收缩隐藏到屏幕边缘的时候，突出来的那一点点厚度
     */
    public static final int HIDDEN_SIZE = 5;
    /**
     * 窗口隐藏时的定时器
     */
    public static Timer hideTimer;
    /**
     * 托盘对象
     */
    public static SystemTray tray;
    /**
     * 托盘对象的图标
     */
    public static TrayIcon trayIcon;
    /**
     * 窗口对象
     */
    public static JWindow window;
    /**
     * 屏幕大小对象 - 含有各种尺寸值（宽、高、缩放比例等）
     */
    public static Dimension screenSize;
    /**
     * 托盘默认文字
     */
    public static String trayIconText = "T";
    /**
     * 项目默认字体风格
     */
    public final static String fontName = "微软雅黑";
}

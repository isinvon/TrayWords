package com.sinvon.traywords.action;

import com.sinvon.traywords.utils.BaiCiZhanAPIUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.sinvon.traywords.common.SysConstant.*;

/**
 * 托盘行为
 *
 * @author sinvon
 * @create 2024年8月14日17:22:18
 */
public class TrayWordsAction {
    /**
     * 显示窗口
     *
     * @param window
     */
    public static void showWindow(JWindow window) {
        if (window != null && !window.isVisible()) {
            window.setVisible(true);
        }
        if (isHidden) {
            showFrame(window, Toolkit.getDefaultToolkit().getScreenSize());
        }
    }

    /**
     * 隐藏窗口
     *
     * @param window
     */
    public static void hideWindow(JWindow window) {
        if (window != null && window.isVisible()) {
            window.setVisible(false);
        }
    }

    /**
     * 退出应用
     *
     * @param trayIcon
     */
    public static void exitApplication(TrayIcon trayIcon) {
        if (tray != null && trayIcon != null) {
            tray.remove(trayIcon);
        }
        System.exit(0);
    }

    /**
     * 隐藏窗口到边缘
     *
     * @param window     要隐藏的窗口
     * @param screenSize 屏幕的尺寸，用于计算窗口位置
     */
    public static void hideToEdge(JWindow window, Dimension screenSize) {
        // 获取窗口当前的位置
        Point location = window.getLocation();

        // 判断窗口是否在屏幕的左侧边缘
        if (location.x <= EDGE_THRESHOLD) {
            // 将窗口移动到屏幕左侧边缘，并调整宽度为隐藏尺寸
            window.setLocation(0, location.y);
            window.setSize(HIDDEN_SIZE, originalHeight);
        }
        // 判断窗口是否在屏幕的右侧边缘
        else if (location.x >= screenSize.width - originalWidth - EDGE_THRESHOLD) {
            // 将窗口移动到屏幕右侧边缘，并调整宽度为隐藏尺寸
            window.setLocation(screenSize.width - HIDDEN_SIZE, location.y);
            window.setSize(HIDDEN_SIZE, originalHeight);
        }
        // 判断窗口是否在屏幕的顶部边缘
        else if (location.y <= EDGE_THRESHOLD) {
            // 将窗口移动到屏幕顶部边缘，并调整高度为隐藏尺寸
            window.setLocation(location.x, 0);
            window.setSize(originalWidth, HIDDEN_SIZE);
        }
        // 判断窗口是否在屏幕的底部边缘
        else if (location.y >= screenSize.height - originalHeight - EDGE_THRESHOLD) {
            // 将窗口移动到屏幕底部边缘，并调整高度为隐藏尺寸
            window.setLocation(location.x, screenSize.height - HIDDEN_SIZE);
            window.setSize(originalWidth, HIDDEN_SIZE);
        }

        // 设置隐藏状态标志为true
        isHidden = true;
    }


    /**
     * 显示窗口并调整其位置和大小
     * 当窗口从屏幕的顶部、底部或两侧移出时，将其重新定位到相应的边缘，并恢复到原始大小
     *
     * @param window     要显示和重新定位的JWindow对象
     * @param screenSize 屏幕的尺寸，用于计算窗口位置
     */
    public static void showFrame(JWindow window, Dimension screenSize) {
        // 获取窗口的当前位置
        Point location = window.getLocation();
        // 检查窗口是否从左侧移出
        if (location.x == 0) {
            // 将窗口定位到屏幕的左侧边缘，并恢复到原始大小
            window.setLocation(0, location.y);
            window.setSize(originalWidth, originalHeight);
        } else if (location.x == screenSize.width - HIDDEN_SIZE) {
            // 检查窗口是否从右侧移出，并将其定位到屏幕的右侧边缘，恢复到原始大小
            window.setLocation(screenSize.width - originalWidth, location.y);
            window.setSize(originalWidth, originalHeight);
        } else if (location.y == 0) {
            // 检查窗口是否从顶部移出，并将其定位到屏幕的顶部边缘，恢复到原始大小
            window.setLocation(location.x, 0);
            window.setSize(originalWidth, originalHeight);
        } else if (location.y == screenSize.height - HIDDEN_SIZE) {
            // 检查窗口是否从底部移出，并将其定位到屏幕的底部边缘，恢复到原始大小
            window.setLocation(location.x, screenSize.height - originalHeight);
            window.setSize(originalWidth, originalHeight);
        }
        // 设置窗口隐藏状态为false，表示窗口已被显示
        isHidden = false;
    }


    /**
     * 设置窗口置顶
     */
    public static void setAlwaysOnTop() {
        // 将窗口置顶于所有应用之上，包括任务栏
        window.setAlwaysOnTop(true);
    }

    /**
     * 窗口监听 - 点击窗口切换单词
     *
     * @param
     */
    public static void clickWindow() {
        // 添加监听窗口点击事件();
        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 重新赋值
                words = BaiCiZhanAPIUtils.getRandomWords();
                System.out.println("你点击了我");
            }
        });
    }

}

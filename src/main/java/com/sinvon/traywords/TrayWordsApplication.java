package com.sinvon.traywords;

import com.sinvon.traywords.action.MouseAction;
import com.sinvon.traywords.action.TrayWordsAction;
import com.sinvon.traywords.common.SysConstant;
import com.sinvon.traywords.gui.TrayWordsGUI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// ---
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.sinvon.traywords.common.SysConstant.*;


/**
 * @author sinvon
 * @since 2024年8月14日17:25:11
 */
// @SuppressWarnings("all")
@Slf4j
@SpringBootApplication
public class TrayWordsApplication {
    public static void main(String[] args) throws AWTException {
        // 启动 Spring 应用
        SpringApplication.run(TrayWordsApplication.class, args);

        // 强制关闭无头模式（如果意外启用）避免启动失败 - 因为有些系统不支持界面显示，但是支持图标嵌入
        System.setProperty("java.awt.headless", "false");

        // 检查系统是否支持系统托盘
        if (!SystemTray.isSupported()) {
            log.error("SystemTray is not supported");
            return;
        }
        log.info("SystemTray is supported!");

        // 创建托盘图标对象
        TrayWordsGUI trayWordsGUI = new TrayWordsGUI();
        TrayIcon trayIcon = trayWordsGUI.trayIcon();

        // 获取系统托盘对象
        tray = SystemTray.getSystemTray();

        // 将托盘图标添加到系统托盘
        tray.add(trayIcon);

        // // 创建一个 JWindow 窗口对象，并且赋值给全局常量
        window = new JWindow();

        // 设置窗口置顶
        TrayWordsAction.setAlwaysOnTop();

        // 设置窗口背景为全透明
        window.setBackground(new Color(0, 0, 0, 0));

        // 将窗口类型设置为工具窗口，确保在所有窗口（包括任务栏）之上
        window.setType(Window.Type.UTILITY);

        // 创建鼠标滚轮监听事件
        window.addMouseWheelListener(e -> {
            // 不论滚轮方向,都触发
            if (e.getWheelRotation() != 0) {

                System.out.println("滚轮滚动了");

                // 清除window中所有对象 - 避免多个面板折叠在一起
                window.getContentPane().removeAll();

                // 创建面板对象
                JPanel panel = trayWordsGUI.panel();

                // 将面板添加到窗口
                window.add(panel);

                // 调整窗口大小以适应内容（让他适应其子组件的首选大小和布局）
                window.pack();
            }
        });

        // 添加鼠标点击事件
        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("鼠标点击了");

                // 清除window中所有对象 - 避免多个面板折叠在一起
                window.getContentPane().removeAll();

                // 创建面板对象
                JPanel panel = trayWordsGUI.panel();

                // 将面板添加到窗口
                window.add(panel);

                // 调整窗口大小以适应内容（让他适应其子组件的首选大小和布局）
                window.pack();

                // 记录窗口的原始宽度
                originalWidth = window.getWidth();
                // 记录窗口的原始高度
                originalHeight = window.getHeight();
            }
        });

        // 创建面板对象
        JPanel panel = trayWordsGUI.panel();

        // 将面板添加到窗口
        window.add(panel);
        // 调整窗口大小以适应内容（让他适应其子组件的首选大小和布局）
        window.pack();

        // 记录窗口的原始宽度
        originalWidth = window.getWidth();
        // 记录窗口的原始高度
        originalHeight = window.getHeight();

        // 获取屏幕尺寸
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 计算窗口的 x 坐标
        int x = screenSize.width / 2 - window.getWidth() / 2;
        // 计算窗口的 y 坐标，考虑任务栏高度
        int y = screenSize.height - window.getHeight() - 40;
        // 设置窗口位置
        window.setLocation(x, y);

        // 启动鼠标监听
        MouseAction.addMouseListener();
    }
}


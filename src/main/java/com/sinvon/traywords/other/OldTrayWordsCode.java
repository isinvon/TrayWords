package com.sinvon.traywords.other;

// ---

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer; // 注意这是 new Timer 的库，不导入的话就会自动使用 javax.swing中的Timer，然后会导致运行报错！
import java.util.TimerTask;

/**
 * 本项目的原始代码 - 重构前
 * @author sinvon
 * @since 2024年8月15日14:57:20
 */
public class OldTrayWordsCode {
    private static Point initialClick;
    private static boolean isHidden = false;
    private static int originalWidth;
    private static int originalHeight;
    private static final int EDGE_THRESHOLD = 10; // 边缘检测的阈值
    private static final int HIDDEN_SIZE = 5; // 窗口隐藏时的厚度
    private static Timer hideTimer;

    public static void main(String[] args) throws AWTException {

        // 强制关闭无头模式（如果意外启用）避免启动失败 - 因为有些系统不支持界面显示，但是支持图标嵌入
        System.setProperty("java.awt.headless", "false");

        // 检查系统是否支持系统托盘
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        System.out.println("SystemTray is supported!");

        JWindow window = new JWindow(); // 使用JWindow代替JFrame
        window.setBackground(new Color(0, 0, 0, 0));  // 全透明背景

        // 将窗口类型设置为工具窗口，确保在所有窗口（包括任务栏）之上
        window.setType(Window.Type.UTILITY);

        JLabel label = new JLabel("Drag Me!");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setOpaque(false);  // 透明标签背景

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 100));  // 半透明背景
        panel.add(label);

        window.add(panel);
        window.pack();

        originalWidth = window.getWidth();
        originalHeight = window.getHeight();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width / 2 - window.getWidth() / 2;
        int y = screenSize.height - window.getHeight() - 40;  // 40 是任务栏大约的高度
        window.setLocation(x, y);

        // 将窗口置顶于所有应用之上，包括任务栏
        window.setAlwaysOnTop(true);

        // 添加鼠标监听器，使窗口可拖动
        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                window.getComponentAt(initialClick);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (isHidden) {
                    showFrame(window, screenSize);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isHidden) {
                    if (hideTimer != null) {
                        hideTimer.cancel();
                    }
                    hideTimer = new Timer(true); // 创建一个新的Timer, 使用守护线程
                    hideTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            hideToEdge(window, screenSize);
                        }
                    }, 1000); // 鼠标移开1秒后收缩窗口
                }
            }
        });

        window.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = window.getLocation().x;
                int thisY = window.getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;

                window.setLocation(X, Y);

                // 检测是否接近屏幕边缘
                if (X <= EDGE_THRESHOLD || X >= screenSize.width - originalWidth - EDGE_THRESHOLD ||
                        Y <= EDGE_THRESHOLD || Y >= screenSize.height - originalHeight - EDGE_THRESHOLD) {
                    hideToEdge(window, screenSize);
                } else {
                    showFrame(window, screenSize);
                }
            }
        });

        window.setVisible(true);  // 在设置类型之后才显示窗口
    }

    // 隐藏窗口到屏幕边缘
    private static void hideToEdge(JWindow window, Dimension screenSize) {
        Point location = window.getLocation();
        if (location.x <= EDGE_THRESHOLD) {
            window.setLocation(0, location.y);
            window.setSize(HIDDEN_SIZE, originalHeight);
        } else if (location.x >= screenSize.width - originalWidth - EDGE_THRESHOLD) {
            window.setLocation(screenSize.width - HIDDEN_SIZE, location.y);
            window.setSize(HIDDEN_SIZE, originalHeight);
        } else if (location.y <= EDGE_THRESHOLD) {
            window.setLocation(location.x, 0);
            window.setSize(originalWidth, HIDDEN_SIZE);
        } else if (location.y >= screenSize.height - originalHeight - EDGE_THRESHOLD) {
            window.setLocation(location.x, screenSize.height - HIDDEN_SIZE);
            window.setSize(originalWidth, HIDDEN_SIZE);
        }
        isHidden = true;
    }

    // 显示窗口
    private static void showFrame(JWindow window, Dimension screenSize) {
        Point location = window.getLocation();
        if (location.x == 0) {
            window.setLocation(0, location.y);
            window.setSize(originalWidth, originalHeight);
        } else if (location.x == screenSize.width - HIDDEN_SIZE) {
            window.setLocation(screenSize.width - originalWidth, location.y);
            window.setSize(originalWidth, originalHeight);
        } else if (location.y == 0) {
            window.setLocation(location.x, 0);
            window.setSize(originalWidth, originalHeight);
        } else if (location.y == screenSize.height - HIDDEN_SIZE) {
            window.setLocation(location.x, screenSize.height - originalHeight);
            window.setSize(originalWidth, originalHeight);
        }
        isHidden = false;
    }
}

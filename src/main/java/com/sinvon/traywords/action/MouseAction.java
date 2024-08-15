package com.sinvon.traywords.action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer; //// 注意这是 new Timer 的库，不导入的话就会自动使用 javax.swing中的Timer，然后会导致运行报错！
import java.util.TimerTask;

import static com.sinvon.traywords.common.SysConstant.*;

/**
 * 鼠标动作
 * @author sinvon
 * @since 2024年8月14日17:24:29
 */
public class MouseAction {
    /**
     * 添加鼠标监听器
     */
    public static void addMouseListener() {
        // 添加鼠标监听器，使窗口可拖动
        window.addMouseListener(new MouseAdapter() {
            /**
             * 鼠标按下
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                window.getComponentAt(initialClick);
            }

            /**
             * 鼠标进入
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isHidden) {
                    TrayWordsAction.showWindow(window);
                }
            }

            /**
             * 鼠标离开
             * @param e the event to be processed
             */
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
                            TrayWordsAction.hideToEdge(window, screenSize);
                        }
                    }, 1000); // 鼠标移开1秒后收缩窗口
                }
            }
        });

        window.addMouseMotionListener(new MouseAdapter() {
            /**
             * 鼠标拖动
             * @param e the event to be processed
             */
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
                    TrayWordsAction.hideToEdge(window, screenSize);
                } else {
                    TrayWordsAction.showWindow(window);
                    // 避免拖动的时候不会保持收缩时候的宽度 - 保持收缩前的宽度
                    window.setSize(originalWidth, originalHeight);
                }
            }
        });

        window.setVisible(true);  // 在设置类型之后才显示窗口
    }
}

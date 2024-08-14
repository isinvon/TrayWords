package com.sinvon.traywords.gui;

import com.sinvon.traywords.action.TrayWordsAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.sinvon.traywords.common.SysConstant.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * @author sinvon
 * @description: 托盘界面
 * @since 2024年8月14日17:24:53
 */
@Slf4j
@Component
public class TrayWordsGUI {

    /**
     * 创建托盘图标图像
     * @return
     */
    public static Image createTrayIconImage() {
        final int width = 16; // 图标宽度
        final int height = 16; // 图标高度
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // 背景颜色和透明度
        Color backgroundColor = new Color(0, 0, 0, 128); // 半透明黑色背景
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);

        // 图标形状（圆形）
        g2d.setColor(new Color(255, 255, 255, 255)); // 纯白色
        g2d.fillOval(0, 0, width, height);

        // 绘制文字
        Color textColor = Color.RED; // 文字颜色
        Font font = new Font("Arial", Font.BOLD, 10); // 文字大小
        g2d.setColor(textColor);
        g2d.setFont(font);

        // 计算文字位置
        FontMetrics metrics = g2d.getFontMetrics(font);
        String text = "W";
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getAscent();
        int x = (width - textWidth) / 2;
        int y = (height + textHeight) / 2 - 2; // 文字垂直居中

        g2d.drawString(text, x, y);

        g2d.dispose();
        return bufferedImage;
    }

    /**
     * 创建托盘上下文菜单
     *
     * @return
     */
    public PopupMenu popupMenu() {
        // 创建上下文菜单
        PopupMenu popupMenu = new PopupMenu();
        // 定义操作与对应的方法 - Runnable为方法
        HashMap<String, Runnable> menuActions = new HashMap<>();
        menuActions.put("show", () -> TrayWordsAction.showWindow(window));
        menuActions.put("hide", () -> TrayWordsAction.hideWindow(window));
        menuActions.put("exit", () -> TrayWordsAction.exitApplication(trayIcon));

        // 依次给每个“托盘上下文对象”赋于监听器
        for (HashMap.Entry<String, Runnable> entry : menuActions.entrySet()) {
            // 每次都新建一个菜单对象
            MenuItem item = new MenuItem(entry.getKey());
            // 为菜单项添加动作监听器，监听器中的方法指定为菜单项的点击事件
            item.addActionListener(e -> entry.getValue().run());
            // 将菜单项添加到上下文菜单
            popupMenu.add(item);
        }
        return popupMenu;
    }

    /**
     * 创建托盘图标对象
     *
     * @return
     */
    public TrayIcon trayIcon() {
        // 创建托盘图标图像
        Image image = createTrayIconImage();
        // 创建带有图像和名称的托盘图标对象
        trayIcon = new TrayIcon(image, "TrayWords");
        // 创建上下文菜单
        PopupMenu popupMenu = popupMenu();
        // 设置托盘图标的上下文菜单
        trayIcon.setPopupMenu(popupMenu);
        // 自动调整托盘图标图像大小
        trayIcon.setImageAutoSize(true);
        // 返回托盘图标对象
        return trayIcon;
    }

    /**
     * 创建面板对象 - 就是显示文字的主体部分
     *
     * @return
     */
    public JPanel panel() {
        // 创建一个 JLabel 标签对象
        JLabel label = new JLabel("Drag Me!");
        // 设置标签字体
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        // 设置标签文字颜色为白色
        label.setForeground(Color.WHITE);
        // 设置标签背景透明
        label.setOpaque(false);

        // 创建一个 JPanel 面板对象
        JPanel panel = new JPanel();
        // 设置面板背景为半透明
        panel.setBackground(new Color(0, 0, 0, 100));
        // 将标签添加到面板
        panel.add(label);
        // 返回面板对象
        return panel;
    }
}

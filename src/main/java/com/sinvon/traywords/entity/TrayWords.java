package com.sinvon.traywords.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description:  traywords 实体类
 * @author sinvon
 * @since 2024年8月14日17:24:53
 */
@Data
// @TableName("tray_words")
public class TrayWords {
    // @TableId
    private Long id;
    /**
     *  文本内容
     */
    private String content;
}

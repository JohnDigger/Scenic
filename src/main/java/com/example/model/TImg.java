package com.example.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 贾佳
 * @date 2021/10/25 22:40
 */
@Data
@Accessors(chain = true)
@TableName("t_img")
public class TImg {
    @TableId
    private int id;
    private String imgPath;
}

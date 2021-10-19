package com.example.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author astupidcoder
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "scenic")
public class Scenic extends Model {

    private static final long serialVersionUID = 1L;
    @Id
    private int id;
    @NotNull(message = "不能为空")
    @NotEmpty(message = "此字段不可以为空")
    private String name;

    private String list;

    private String picturePath;

    private String text;

    private String videoPath;

    private String scenicType;

    @TableField(value = "audio_id", exist = false)
    private int audioid;
    @TableField(value = "scenic_id", exist = false)
    private int scenicId;
    @TableField(value = "box_id", exist = false)
    private int boxId;
    @TableField(value = "scenic_name", exist = false)
    private String scenicName;
    @TableField(value = "audio_url", exist = false)
    private String audioUrl;
    @TableField(value = "audio_name", exist = false)
    private String audioName;
    @TableField(value = "audio_money", exist = false)
    private float audioMoney;


}

package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author 贾佳
 * @date 2021/9/25 19:52
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@TableName("t_type")
public class Ttype extends Model {
    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @Column(name = "scenic_type")
    private String scenicType;


}

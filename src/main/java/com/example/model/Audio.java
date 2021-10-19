package com.example.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "audio")
public class Audio extends Model {

    @Id
    private int audioid;
    private int scenicId;
    private String scenicName;
    private String audioUrl;
    private String audioName;
    private String audioMoney;
    private String audioNameAndMoney;


}

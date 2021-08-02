package com.example.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

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
@Table(name="t_buy")
public class TBuy extends Model{

  @Id
  private int buyId;
  private int scenicId;
  private String scenicName;
  private int userId;
  private String nickName;
  private int audioId;
  private String audioName;
  private float audioMoney;
  private String buyTime;
  @TableField(value = "buy_count", exist = false)
  private int buyCount;
}

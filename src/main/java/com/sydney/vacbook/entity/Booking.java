package com.sydney.vacbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Group45
 * @since 2021-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "booking_id", type = IdType.AUTO)
    private Integer bookingId;

    private Integer userId;

    private Integer vaccineId;

    private String bookingTimezone;

    @TableField(exist = false)
    private String date;
    @TableField(exist = false)
    private String time;


}

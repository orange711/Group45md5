package com.sydney.vacbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BookingVO {
    private static final long serialVersionUID = 1L;
    private String date;
    private String period;
    private String vaccine;
    private String user;
}

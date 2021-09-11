package com.sydney.vacbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Vaccine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vaccine_id", type = IdType.AUTO)
    private Integer vaccineId;

    private String vaccineType;

    private String vaccineName;

    private String vaccineDescription;

    private Integer adminId;

    private Integer vaccineAmount;


}

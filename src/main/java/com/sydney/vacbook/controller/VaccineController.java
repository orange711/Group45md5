package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sydney.vacbook.entity.Vaccine;
import com.sydney.vacbook.mapper.VaccineMapper;
import com.sydney.vacbook.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Group45
 * @since 2021-09-11
 */
@RestController
@RequestMapping("/vacbook/vaccine")
@Controller
public class VaccineController {
    @Autowired
    IVaccineService iVaccineService;
    @Autowired
    VaccineMapper vaccineMapper;

    //增加一条vaccine
    public boolean saveVaccine(Vaccine vaccine) {
        boolean saveVaccine = iVaccineService.save(vaccine);
        return saveVaccine;
    }

    //    更新一条vaccine
    public boolean updateVaccine(Vaccine vaccine) {
        boolean updateVaccineById = iVaccineService.updateById(vaccine);
        return updateVaccineById;
    }

    //    get vaccine according to ID
    public Vaccine getVaccine(int id) {
        Vaccine getVaccineById = iVaccineService.getById(id);
        return getVaccineById;

    }

    // get all vaccine according to the adminID
    @RequestMapping("/{admin_id}/getvaccine")

    public List<Vaccine> getVaccineListByAdminId(@PathVariable("admin_id") int adminId) {

        QueryWrapper<Vaccine> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("admin_id", adminId);
        List<Vaccine> getVaccineListByAdminId = iVaccineService.list(sectionQueryWrapper);
        return getVaccineListByAdminId;

    }
    //del vaccine
    @GetMapping("/delvaccine")
    public boolean delVaccine(Vaccine vaccine){
        boolean removeVaccineById = iVaccineService.removeById(vaccine.getVaccineId());
        return removeVaccineById;
    }



}

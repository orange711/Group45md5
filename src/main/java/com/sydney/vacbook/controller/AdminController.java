package com.sydney.vacbook.controller;


import com.sydney.vacbook.entity.Admin;
import com.sydney.vacbook.mapper.AdminMapper;
import com.sydney.vacbook.service.IAdminService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Group45
 * @since 2021-09-11
 */

//这层应该只调用service才是规范的代码
//    以下代码只是练习以及加深对MVC架构的理解
@RestController
@RequestMapping("/vacbook/admin")
@Controller
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private IAdminService iAdminService;

    List<Admin> list=new ArrayList<>();


    @GetMapping("/addAdmin")
    public int addAdmin(Admin admin) {
        int insert = adminMapper.insert(admin);
        return insert;
    }

    @GetMapping("/updateAdmin")
    public int updateAdmin(Admin admin){


        int update = adminMapper.updateById(admin);
        return update;
    }

    @GetMapping("adminLogin")
    public String getLogin(Admin admin, Map<Object, Object> map) {

        //System.out.println(admin);

        //调用管理员查询方法
        list=iAdminService.list();
        String str=list.toString();

        //System.out.println(list);

        if (!str.equals("[]")) {

            map.put("adminList", list.get(0));
//            plateController.getPlate(map); 得到admin下面的疫苗表
//            UserController.getUser(map);得到admin下面的预定用户

            return "redirect:index";//重定向
        } else {

            return "redirect:index";//重定向
        }
    }


}

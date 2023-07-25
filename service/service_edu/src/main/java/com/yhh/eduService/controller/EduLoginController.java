package com.yhh.eduService.controller;

import com.yhh.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin
public class EduLoginController {


    //login
    @PostMapping("login")  //->修改api文件夹login.js为本地接口路径
    public Result login(){
        return  Result.OK().data("token","admin");//token和前端user.js一致

    }

//    info
    @GetMapping("info")
    public Result info(){
        return  Result.OK().data("roles","[admin]").data("name","admin").data("avatar","C:/Users//Administrator//Desktop//lbxx20230427160820.jpg");

    }


}

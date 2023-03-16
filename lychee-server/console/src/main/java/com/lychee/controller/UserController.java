package com.lychee.controller;


import com.lychee.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang
 * @since 2021-11-17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test1")
    public Result<String> test(){
        String str = "aaa";
        System.out.println("1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2");
        return Result.ok("hhhhhhhhhhh");
    }

}

package com.chc.controller;

import com.chc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/say/{str}")
    public String say(@PathVariable("str") String str) {
        return userService.say(str);
    }

    @GetMapping("/sing/{music}")
    public String sing(@PathVariable("music") String music) {
        return userService.sing(music);
    }
}

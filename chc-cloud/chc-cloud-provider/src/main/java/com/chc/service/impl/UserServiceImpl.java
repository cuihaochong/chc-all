package com.chc.service.impl;

import com.chc.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/9/11
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String say(String str) {
        return "what are you say:" + str;
    }

    @Override
    public String sing(String music) {
        return "唱歌:" + music;
    }
}

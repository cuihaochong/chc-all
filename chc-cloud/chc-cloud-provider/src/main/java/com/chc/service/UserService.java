package com.chc.service;

/**
 * Description: 用户接口
 *
 * @author cuihaochong
 * @date 2019/9/11
 */
public interface UserService {

    /**
     * 说话
     *
     * @param str 说的话,字符串
     * @return 说的话
     */
    String say(String str);


    /**
     * 唱歌
     *
     * @param music 音乐
     * @return 唱歌了
     */
    String sing(String music);
}

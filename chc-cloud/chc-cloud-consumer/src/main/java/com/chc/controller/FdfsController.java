package com.chc.controller;

import com.chc.base.Result;
import com.chc.client.FeignFdfsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/11/19
 */
@RestController
@RequestMapping("/fdfs")
public class FdfsController {

    @Autowired
    FeignFdfsClient feignFdfsClient;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    Result upload(@RequestPart("file") MultipartFile file) {
        return feignFdfsClient.upload(file);
    }
}

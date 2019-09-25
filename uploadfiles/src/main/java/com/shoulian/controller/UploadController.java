package com.shoulian.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class UploadController {

    @Value("${files.upload.path}")
    private String filePath;

    @RequestMapping("/zq")
    public String toUpload(){
        System.out.println(filePath);///mnt/www/
        return "uploadImg";
    }
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(MultipartFile file[], HttpServletRequest request){
        //获取当前时间
        long millis = System.currentTimeMillis();
        //获取日期对象
        Date date = new Date(millis);
        //转换日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        File fileDir = new File(filePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }

        return "";
    }

}

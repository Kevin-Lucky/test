package com.itheima.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
public class UploadController {

    @Value("${file.upload.path}")
    private String path;

    @RequestMapping("/index")
    public String toUpload(){
        return "upload";
    }

    @RequestMapping("/indexs")
    public String toUploads(){
        return "uploads";
    }

    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(MultipartFile file,HttpServletRequest request){

        try {
            //创建文件在服务器端的存放位置路径
            //String path = request.getServletContext().getRealPath("/upload");
            //String path="/mnt/www/upload";

            //String path="/mnt/www/upload";

            System.out.println(path);
            File fileDir=new File(path);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            //生成文件在服务器端存放的名字
            String fileSuffix = file.getOriginalFilename();
            //System.out.println(fileSuffix);
            String fileName= UUID.randomUUID().toString()+fileSuffix;
            //System.out.println(fileName.endsWith(".jps"));
            if(fileName.endsWith(".jpg") || fileName.endsWith(".bmp") || fileName.endsWith(".png")) {
                File files = new File(fileDir + "/" + fileName);
                //上传
                file.transferTo(files);
                return "上传成功";
            }
            else{
                return "上传失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    @RequestMapping(value="/upload/batch",method = RequestMethod.POST)
    @ResponseBody
    public String uploadFiles(MultipartFile[] file,HttpServletRequest request){

        try {
            //创建文件在服务器端的存放位置路径
            //String path = request.getServletContext().getRealPath("/upload");

            String path="D:/upload";
            System.out.println(path);
            File fileDir=new File(path);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            for(int i=0;i<file.length;i++){
                String fileSuffix=file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf("."));
                String fileName=UUID.randomUUID().toString()+fileSuffix;
                File files=new File(fileDir+"/"+fileName);
                //上传
                file[i].transferTo(files);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }

}

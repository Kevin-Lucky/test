package com.pingan.controller;

import com.pingan.domain.MessageDTO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

//localhost:8080/file/down
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.download.fileName}")
    private String fileName;

    @Value("${file.new.fileName}")
    private String newFileName;



    @GetMapping("/down")
    public ResponseEntity down() {

        File file = null;
        HttpHeaders headers = null;
        String urlPath = "";

        try {



            urlPath = getPathTest();


            file = new File(urlPath);

            if ( file.length() > 0 ){
                String suffixType =urlPath.substring(urlPath.lastIndexOf("."));

//                System.out.println(suffixType);//.pdf

                String newFileNameSuffix =newFileName+suffixType;

                headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Content-Disposition", "attachment; filename=" +  URLEncoder.encode(newFileNameSuffix, "UTF-8"));
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                headers.add("Last-Modified", new Date().toString());
                headers.add("ETag", String.valueOf(System.currentTimeMillis()));

                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(new FileSystemResource(file));
            }

        } catch (IOException e) {
            e.printStackTrace();
            //log.info("dfsId:{}下载文件失败，cause:{}",e);
        }


        return ResponseEntity.ok().body(new MessageDTO(urlPath,"2000"));

    }

    /**
     * 当打成一个jar包后，整个jar包是一个文件，只能使用流的方式读取资源，这时候就不能通过File来操作资源了
     * 只能创建临时文件，用流读取文件内容输出到临时文件中，来获取临时文件路径代替项目中文件路径
     * @return
     */
    public String getPathTest() throws IOException {

//        String fileName = "重庆市互联网医疗服务监管平台接入开发规范-20200313（新）.pdf";

//        System.out.println(fileName);
        String filepath = getUploadResource(fileName);
//        System.out.println(filepath);
        return filepath;
    }


    public String getUploadResource(String fileName) throws IOException {
        //返回读取指定资源的输入流
//        InputStream is = this.getClass().getResourceAsStream("/excelTemplates/" + fileName);
//        File file1 = ResourceUtils.getFile("classpath:excelTemplates/" + fileName);
//        InputStream is = new FileInputStream(file1);
        ClassPathResource resource =new ClassPathResource("/excelTemplates/"+fileName);

        if (!resource.exists()){
            return "没有该文件";
        }
        InputStream is = resource.getInputStream();

        //若文件已存在，则返回的filePath中含有"EXIST"，则不需再重写文件
        //String filePath  = createFile(fileName);

        String filePath  = createFile(fileName);

        //文件不存在，则创建流输入默认数据到新文件
//        if (!filePath.contains("EXIST")) {
//            File file = new File(filePath);//此时创建了一个名为重庆市互联网医疗服务监管平台接入开发规范-20200313（新）.pdf的空文件
//            inputStreamToFile(is, file);
//            return filePath;
//        }
        File file = new File(filePath);//此时创建了一个名为test.doc的空文件
        inputStreamToFile(is, file);
        return filePath;
    }


    /**
     * File.separator  == /
     * @param filename
     * @return
     */
    public String createFile(String filename) throws IOException {

        String path = System.getProperty("user.dir");

        String dirPath = path + File.separator + "temporaryFiles";
//        System.out.println(dirPath);

        File dir = new File(dirPath);
        if(!dir.exists())
        dir.mkdirs();

        //create file
        String filePath = dirPath + File.separator + filename;
        File file = new File(filePath);

        file.createNewFile();
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return filePath;
//        }
//        return "EXIST" + filePath;
        return filePath;
    }

    public void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


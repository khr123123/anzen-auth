package org.khr.anzenauth.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

/**
 * 上传接口示例
 * 前端可以使用 Arco Upload 组件上传文件
 * 返回 JSON：{ "code": 0, "url": "文件访问地址" }
 *
 * author KK
 * create 2025-09-22-13:10
 */
@RestController
@RequestMapping("/upload")
public class UploadController {


    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("code", 1, "msg", "文件为空"));
        }
        try {
            String UPLOAD_DIR = "C:/anzenauth/uploads/"; // 绝对路径
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
            String newFilename = UUID.randomUUID() + suffix;

            File dest = new File(dir, newFilename);
            file.transferTo(dest);

            String fileUrl = "http://localhost:8080/api/uploads/" + newFilename;
            return ResponseEntity.ok(Map.of("code", 0, "url", fileUrl));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("code", 1, "msg", "上传失败"));
        }
    }

}

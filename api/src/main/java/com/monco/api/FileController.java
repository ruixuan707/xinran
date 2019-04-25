package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.core.service.common.FastFileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/13 10:22
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    private static final String[] imageFormat = {"bmp", "jpg", "png", "gif"};

    @Autowired
    private FastFileStorageService fastFileStorageService;

    @PostMapping(value = "uplfmdFileToFastList")
    public ApiResult uploadImage(@RequestParam MultipartFile[] file) throws Exception {
        List<String> imageFormats = Arrays.asList(imageFormat);
        List<String> paths = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(file)) {
            for (MultipartFile multipartFile : file) {
                String fileName = multipartFile.getOriginalFilename();
                if (!imageFormats.contains(StringUtils.substringAfterLast(fileName, "."))) {
                    return ApiResult.error("图片格式不正确");
                }
            }
            for (MultipartFile multipartFile : file) {
                String fileName = multipartFile.getOriginalFilename();
                log.info("上传的图片名称{0}", fileName);
                String path = fastFileStorageService.uploadFile(multipartFile);
                paths.add(path);
            }
        }
        return ApiResult.ok(paths);
    }

    @PostMapping(value = "excel")
    public ResponseEntity<ApiResult> uploadFile(@RequestParam MultipartFile file) throws Exception {
        List<String> paths = new ArrayList<>();
        if (file != null) {
            String fileName = file.getOriginalFilename();
            if (!StringUtils.endsWithIgnoreCase(fileName, ".xlsx") || !StringUtils.endsWithIgnoreCase(fileName, ".xls")) {
                log.info("上传的excel文件名称{0}", fileName);
                String path = fastFileStorageService.uploadFile(file);
                paths.add(path);
            } else {
                throw new RuntimeException("excel文件格式不正确");
            }

        }
        return ResponseEntity.ok(ApiResult.ok(paths));
    }
}

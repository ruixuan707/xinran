package com.monco.core.service.common;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * FastFileStorageInvoker
 *
 * @author Lijin
 * @version 1.0.0
 */
@Slf4j
@Service
public class FastFileStorageService {

    @Value("${fdfs.server.address}")
    private String serverAddress;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * MultipartFile
     *
     * @param file
     *         文件
     * @return 地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * File
     *
     * @param file
     *         文件
     * @return 地址
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StorePath path = storageClient.uploadFile(inputStream, file.length(),
                FilenameUtils.getExtension(file.getName()), null);
        return getResAccessUrl(path);
    }

    /**
     * 删除
     *
     * @param fileUrl
     *         地址
     * @throws IOException
     */
    public void deleteFile(String fileUrl) throws IOException {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            throw new IOException(e);
        }
    }

    /**
     * 下载
     *
     * @param fileUrl
     *         文件路径
     * @return byte
     */
    public byte[] downloadFile(String fileUrl) {
        StorePath storePath = StorePath.praseFromUrl(fileUrl);
        return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), ins -> {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            int len = 1024;
            byte[] buff = new byte[len];
            int rc;
            while ((rc = ins.read(buff, 0, len)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            return swapStream.toByteArray();
        });
    }

    /**
     * 返回文件上传成功后的地址
     *
     * @param storePath
     *         地址
     * @return 地址
     */
    private String getResAccessUrl(StorePath storePath) {
        if (StringUtils.endsWith(serverAddress, "/")) {
            return StringUtils.join(serverAddress, storePath.getFullPath());
        } else {
            return StringUtils.join(serverAddress, "/", storePath.getFullPath());
        }
    }

}
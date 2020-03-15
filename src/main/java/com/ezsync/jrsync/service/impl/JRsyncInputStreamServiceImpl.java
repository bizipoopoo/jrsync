package com.ezsync.jrsync.service.impl;

import com.ezsync.jrsync.rsync.Rsync;
import com.ezsync.jrsync.service.JRsyncInputStreamService;
import com.ezsync.jrsync.utils.MD5Utils;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JRsyncInputStreamServiceImpl implements JRsyncInputStreamService {

    @Override
    public Map<String, Map<String, Long>> calculateAdl32AndMd5(File file) throws IOException {
        Map<String, Map<String, Long>> compareTable = new HashMap<>();
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        int size = size(file.length());
        byte[] readBytes = new byte[size];
        FileInputStream fileInputStream = new FileInputStream(file);
        int len;
        int off = 0;
        while ((len = fileInputStream.read(readBytes)) > 0) {
            int adler32 = Rsync.adler32(readBytes);
            String md5 = MD5Utils.MD54bytes(readBytes);
            long location = (off << 32) + len;
            Map<String, Long> locateMap = new HashMap<String, Long>() {{
                put(md5, location);
            }};
            compareTable.put(Integer.toHexString(adler32), locateMap);
            off += len;
        }
        return compareTable;
    }

    @Override
    public List<Object> diff(File file, Map<String, Map<String, Long>> compareTable) throws IOException {
        if(file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        int size = size(file.length());
        ByteArrayOutputStream noMatchBuffer = new ByteArrayOutputStream(1024);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte[] readBytes = new byte[size];
        int len;
        while ((len = randomAccessFile.read(readBytes)) > 0) {
            
        }
        return null;
    }

    private int size(long length) {
        int size;
        if (length <= 10 * 1024) { // 10k以下
            size = Rsync.TRUNCK_SIZE_8;
        } else if (length <= 1024 << 10) { // 1M以下
            size = Rsync.TRUNCK_SIZE_125;
        } else if (length <= 1024 << 20) { // 512M以下
            size = Rsync.TRUNCK_SIZE_256;
        } else if (length <= 1024 << 30) { // 1G以下
            size = Rsync.TRUNCK_SIZE_512;
        } else { // 1G以上
            size = Rsync.TRUNCK_SIZE_1024;
        }
        return size;
    }
}

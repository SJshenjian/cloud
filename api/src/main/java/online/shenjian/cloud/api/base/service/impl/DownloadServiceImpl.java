package online.shenjian.cloud.api.base.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import online.shenjian.cloud.api.base.service.DownloadService;
import online.shenjian.cloud.common.enums.Constant;
import online.shenjian.cloud.common.utils.Md5Utils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import static online.shenjian.cloud.common.enums.Constant.BUFFER_SIZE;

@Slf4j
@Service
public class DownloadServiceImpl implements DownloadService {


    @Override
    public ResponseEntity<Object> downloadFile(HttpHeaders headers, String fileId) {
        Path filePath = Paths.get(Constant.FILE_PREFIX + fileId);
        File file = filePath.toFile();
        long fileLength = file.length();

        // 解析 Range 头
        List<HttpRange> ranges = headers.getRange();
        if (ranges.isEmpty()) {
            // 计算分片 MD5
            String fileMD5;
            try {
                fileMD5 = Md5Utils.calculateMD5(Files.readAllBytes(filePath));
            } catch (Exception e) {
                log.error("Failed to calculate MD5 for file {}", fileId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .contentLength(fileLength)
                    .header("File-MD5", fileMD5) // 添加 MD5 响应头
                    .body(new FileSystemResource(file));
        }

        // 处理 Range 请求
        HttpRange range = ranges.get(0);
        long start = range.getRangeStart(fileLength);
        long end = range.getRangeEnd(fileLength);
        long rangeLength = end - start + 1;
        log.info("start: {}, end: {}", start, end);
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");
             FileChannel channel = raf.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate((int) rangeLength);
            channel.position(start);
            channel.read(buffer);
            buffer.flip();

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header("Content-Range", "bytes " + start + "-" + end + "/" + fileLength)
                    .header("Accept-Ranges", "bytes")
                    .contentLength(rangeLength)
                    .body(buffer.array());
        } catch (Exception e) {
            log.error("Failed to process range request for file {}", fileId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // 这种方式会导致原始文件大小为0
//        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                .header("Content-Range", "bytes " + start + "-" + end + "/" + fileLength)
//                .header("Accept-Ranges", "bytes")
//                .contentLength(rangeLength)
//                .body(new ResourceRegion(new FileSystemResource(file), start, rangeLength));
        // 计算当前分片的MD5， 排查用
//        byte[] chunkData;
//        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
//            raf.seek(start);
//            chunkData = new byte[(int) rangeLength];
//            raf.read(chunkData);
//            bytesToHex(chunkData);
//            String md5 = DigestUtils.md5DigestAsHex(chunkData);
//            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                    .header("Content-Range", "bytes " + start + "-" + end + "/" + fileLength)
//                    .header("Accept-Ranges", "bytes")
//                    .header("File-MD5", md5)  // 添加MD5校验头
//                    .contentLength(rangeLength)
//                    .body(new ResourceRegion(new FileSystemResource(file), start, rangeLength));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void downloadStream(String fileId, HttpServletResponse response) {
        Path filePath = Paths.get(Constant.FILE_PREFIX + fileId);
        String fileName = filePath.getFileName().toString();

        try {
            if (!Files.exists(filePath)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                throw new RuntimeException("File not found: " + filePath);
            }

            String contentType = Files.probeContentType(filePath);
            response.setContentType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLengthLong(Files.size(filePath));

            String acceptEncoding = response.getHeader("Accept-Encoding");
            if (acceptEncoding != null && acceptEncoding.contains("gzip")) {
                response.setHeader("Content-Encoding", "gzip");
                try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ);
                     GZIPOutputStream gzipOut = new GZIPOutputStream(response.getOutputStream())) {
                    channel.transferTo(0, channel.size(), Channels.newChannel(gzipOut));
                }
            } else {
                try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ);
                     OutputStream out = new BufferedOutputStream(response.getOutputStream(), BUFFER_SIZE)) {
                    channel.transferTo(0, channel.size(), Channels.newChannel(out));
                }
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException("Failed to download file: " + e.getMessage(), e);
        }
    }
}

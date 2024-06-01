package online.shenjian.cloud.api.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.shenjian.cloud.api.system.model.File;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 附件 服务类
 *
 * @author mazhaoyang
 * @since 2024/1/17
 */
public interface FileService extends IService<File> {

    ResponseVo upload(MultipartFile file) throws IOException;
}
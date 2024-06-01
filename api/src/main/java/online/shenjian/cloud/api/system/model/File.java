package online.shenjian.cloud.api.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author mazhaoyang
 * @since 2024/1/17
 */
@Data
@TableName("sys_file")
@Schema(name = "SysFile", description = "附件")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "file_id", type = IdType.INPUT)
    private String fileId;

    @Schema(description = "文件类型(1:WORD，2:EXCEL，3:PPT，4:PDF，5:TXT)")
    @TableField("file_type")
    private String fileType;

    @Schema(description = "文件大小")
    @TableField("file_size")
    private String fileSize;

    @Schema(description = "文件地址")
    @TableField("file_location")
    private String fileLocation;

    @Schema(description = "文件存储名")
    @TableField("store_name")
    private String storeName;

    @Schema(description = "文件显示名")
    @TableField("show_name")
    private String showName;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    @TableField("create_user")
    private String createUser;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Schema(description = "修改人")
    @TableField("update_user")
    private String updateUser;

    @Schema(description = "删除标志")
    @TableField("del_flag")
    private String delFlag;
}
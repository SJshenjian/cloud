package online.shenjian.cloud.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author shenjian
 * @since 2023-08-25
 */
@Data
@TableName("user")
@Schema(description = "用户表")
public class User implements Serializable {


    @Schema(description = "用户ID")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @Schema(description = "登录账号")
    @TableField("username")
    private String username;

    @Schema(description = "姓名")
    @TableField("name")
    private String name;
}

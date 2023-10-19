package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品收藏
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CollectInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品ID
     */
    private Integer commodityId;

    /**
     * 收藏用户
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String commodityName;

    /**
     * 商品类型
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 状态
     */
    @TableField(exist = false)
    private String status;

}

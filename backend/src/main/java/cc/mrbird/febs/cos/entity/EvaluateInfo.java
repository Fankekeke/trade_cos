package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单评价
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EvaluateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属订单
     */
    private Integer orderId;

    /**
     * 评价用户
     */
    private Integer userId;

    /**
     * 得分
     */
    private BigDecimal score;

    /**
     * 评价内容
     */
    private String remark;

    /**
     * 评价时间
     */
    private String createDate;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 评价图片
     */
    private String images;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 商品类型
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String commodityName;
}

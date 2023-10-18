package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommodityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员编号
     */
    private String code;

    /**
     * 标题
     */
    private String title;

    /**
     * 商品描述
     */
    private String content;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 所属类型
     */
    private Integer typeId;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 规格
     */
    private String model;

    /**
     * 成色状态
     */
    private String quality;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 点击次数
     */
    private Integer clickNum;

    /**
     * 最近点击时间
     */
    private LocalDateTime clickTime;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer storeNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 商品状态（0.上架 1.下架 2.售出）
     */
    private Integer status;

    /**
     * 商品图片
     */
    private String images;


}

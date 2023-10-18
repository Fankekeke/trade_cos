package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 购买用户
     */
    private Integer buyUserId;

    /**
     * 卖出用户
     */
    private Integer sellUserId;

    /**
     * 商品ID
     */
    private Integer commodityId;

    /**
     * 订单价格
     */
    private BigDecimal totalPrice;

    /**
     * 折后价
     */
    private BigDecimal totalAfterPrice;

    /**
     * 支付状态（0.未支付 1.已支付 2.正在发货 3.已发货 4.已收货）
     */
    private Integer payStatus;

    /**
     * 支付类型（1.支付宝 2.银联卡）
     */
    private Integer peyType;

    /**
     * 物流信息
     */
    private String logistics;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;


}

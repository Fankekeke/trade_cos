package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品类别
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommodityType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回收类型编号
     */
    private String code;

    /**
     * 回收类型名称
     */
    private String name;

    /**
     * 图片
     */
    private String images;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;


}

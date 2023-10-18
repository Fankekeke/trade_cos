package cc.mrbird.febs.cos.entity;

import java.io.Serializable;
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


}

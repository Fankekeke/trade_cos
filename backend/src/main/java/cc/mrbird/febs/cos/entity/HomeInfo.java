package cc.mrbird.febs.cos.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 首页展示
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HomeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 首页图片展示
     */
    private String images;


}

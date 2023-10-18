package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品回复
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReplyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 被回复用户
     */
    private Integer replyUserId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 商品ID
     */
    private Integer commodityId;

    /**
     * 回复用户
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 删除标识
     */
    private Integer delFlag;


}

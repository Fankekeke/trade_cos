package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CommodityInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.ICommodityInfoService;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IUserInfoService userService;

    private final ICommodityInfoService commodityInfoService;

    /**
     * 分页获取订单订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单订单信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return baseMapper.selectOrderPage(page, orderInfo);
    }

    /**
     * 获取订单订单详细信息
     *
     * @param orderId ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> detail(Integer orderId) {
        // 订单信息
        OrderInfo order = this.getById(orderId);
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("buyer", null);
                put("sale", null);
                put("goods", null);
            }
        };

        UserInfo buyer = userService.getById(order.getBuyUserId());
        UserInfo sale = userService.getById(order.getSellUserId());

        CommodityInfo commodity = commodityInfoService.getById(order.getCommodityId());

        result.put("buyer", buyer);
        result.put("sale", sale);
        result.put("goods", commodity);
        return result;
    }
}

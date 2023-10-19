package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CommodityInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.ICommodityInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/commodity-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommodityInfoController {

    private final ICommodityInfoService commodityInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取商品信息
     *
     * @param page         分页对象
     * @param commodityInfo 商品信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CommodityInfo> page, CommodityInfo commodityInfo) {
        return R.ok(commodityInfoService.selectCommodityPage(page, commodityInfo));
    }

    /**
     * 获取商品信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(commodityInfoService.list());
    }

    /**
     * 获取商品详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(commodityInfoService.getById(id));
    }

    /**
     * 新增商品信息
     *
     * @param commodityInfo 商品信息
     * @return 结果
     */
    @PostMapping
    public R save(CommodityInfo commodityInfo) {
        // 获取用户信息
        UserInfo user = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, commodityInfo.getUserId()));
        if (user != null) {
            commodityInfo.setUserId(user.getId());
        }
        commodityInfo.setCode("COM-" +System.currentTimeMillis());
        commodityInfo.setCreateTime(DateUtil.formatDateTime(new Date()));
        return R.ok(commodityInfoService.save(commodityInfo));
    }

    /**
     * 修改商品信息
     *
     * @param commodityInfo 商品信息
     * @return 结果
     */
    @PutMapping
    public R edit(CommodityInfo commodityInfo) {
        return R.ok(commodityInfoService.updateById(commodityInfo));
    }

    /**
     * 删除商品信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(commodityInfoService.removeByIds(ids));
    }
}

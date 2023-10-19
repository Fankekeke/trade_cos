package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HomeInfo;
import cc.mrbird.febs.cos.service.IHomeInfoService;
import cn.hutool.core.date.DateUtil;
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
@RequestMapping("/cos/home-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeInfoController {

    private final IHomeInfoService homeInfoService;

    /**
     * 分页获取首页图片信息
     *
     * @param page     分页对象
     * @param homeInfo 首页图片信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<HomeInfo> page, HomeInfo homeInfo) {
        return R.ok();
    }

    /**
     * 获取首页图片信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(homeInfoService.list());
    }

    /**
     * 获取首页图片详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(homeInfoService.getById(id));
    }

    /**
     * 新增首页图片信息
     *
     * @param homeInfo 首页图片信息
     * @return 结果
     */
    @PostMapping
    public R save(HomeInfo homeInfo) {
        return R.ok(homeInfoService.save(homeInfo));
    }

    /**
     * 修改首页图片信息
     *
     * @param homeInfo 首页图片信息
     * @return 结果
     */
    @PutMapping
    public R edit(HomeInfo homeInfo) {
        return R.ok(homeInfoService.updateById(homeInfo));
    }

    /**
     * 删除首页图片信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(homeInfoService.removeByIds(ids));
    }
}

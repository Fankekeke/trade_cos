package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IUserInfoService;
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
@RequestMapping("/cos/user-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoController {

    private final IUserInfoService userInfoService;

    /**
     * 分页获取用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserInfo> page, UserInfo userInfo) {
        return R.ok(userInfoService.selectUserPage(page, userInfo));
    }

    /**
     * 获取用户信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(userInfoService.list());
    }

    /**
     * 获取用户详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(userInfoService.getById(id));
    }

    /**
     * 新增用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PostMapping
    public R save(UserInfo userInfo) {
        userInfo.setCode("UR-" +System.currentTimeMillis());
        userInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(userInfoService.save(userInfo));
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 删除用户信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userInfoService.removeByIds(ids));
    }

}

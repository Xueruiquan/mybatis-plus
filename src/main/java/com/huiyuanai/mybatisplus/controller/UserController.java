package com.huiyuanai.mybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huiyuanai.mybatisplus.entity.User;
import com.huiyuanai.mybatisplus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xue rui quan
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping(value = "/test")
    public String test(){
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setOpenid("openid");
        user.setUsername("灰原哀");
        user.setAddress("名侦探柯南");
        List<User> list = new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            list.add(user);
        }

        // 新增
//        boolean flag = userService.save(user);
//        log.info("新增结果：" + flag);

        // 批量新增
//        boolean flag = userService.saveBatch(list);
//        log.info("新增信息：" + flag);

        // 批量新增 第二个参数：分批处理时，一次性处理的数据条数
//        boolean flag = userService.saveBatch(list,2);
//        log.info("新增信息：" + flag);

        // 新增或者更新 也有批处理，与新增一样
//        boolean flag = userService.saveOrUpdate(user);
//        log.info("新增或者更新：" + flag);

        // 通过主键id查询
//        User user1 = userService.getById("1");
//        log.info("通过主键id查询结果：" + user1);

        // 批量查询 （查询出列表全部信息）
//        List<User> list = userService.list();
//        log.info("批量查询结果：" + list);

        // 条件查询 select * from user where id = '1' and username = 'sherry'
//        User user2 = userService.getOne(new QueryWrapper<User>().eq("id", "1").eq("username","sherry"));
//        log.info("条件查询结果：" + user2);

        // 分页查询
//        int pageNum = 1;
//        int pageSize = 3;
//        IPage<User> userIPage = userService.page(new Page<>(pageNum,pageSize), new QueryWrapper<User>().eq("username","灰原哀"));
//        List<User> list = userIPage.getRecords(); // 数据库查询记录
//        log.info("数据库查询记录结果：" + list);
//        long allPageNum = userIPage.getPages(); // 总页数
//        log.info("总页数：" + allPageNum);

        // 根据id 删除 实现逻辑删除，数据库中的记录不真的删除，只修改 逻辑删除键设定的字段 的状态。
//        boolean flag = userService.removeById(2);
//        log.info("删除信息：" + flag);

        // 根据条件 删除 实现逻辑删除，数据库中的记录不真的删除，只修改 逻辑删除键设定的字段 的状态。
        boolean flag = userService.remove(new QueryWrapper<User>().eq("id",3));
        log.info("删除信息：" + flag);

        return "测试结束";
    }
}

package com.huiyuanai.mybatisplus.service.impl;

import com.huiyuanai.mybatisplus.entity.User;
import com.huiyuanai.mybatisplus.mapper.UserMapper;
import com.huiyuanai.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xue rui quan
 * @since 2020-04-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

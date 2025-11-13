package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Users;
import org.lemon.bugtrackplatformserver.modules.service.UsersService;
import org.lemon.bugtrackplatformserver.modules.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:25
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}





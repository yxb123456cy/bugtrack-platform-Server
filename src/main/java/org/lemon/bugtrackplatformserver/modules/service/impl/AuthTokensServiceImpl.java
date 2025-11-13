package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.AuthTokens;
import org.lemon.bugtrackplatformserver.modules.service.AuthTokensService;
import org.lemon.bugtrackplatformserver.modules.mapper.AuthTokensMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【auth_tokens(Token 存储表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class AuthTokensServiceImpl extends ServiceImpl<AuthTokensMapper, AuthTokens>
    implements AuthTokensService{

}





package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Issues;
import org.lemon.bugtrackplatformserver.modules.service.IssuesService;
import org.lemon.bugtrackplatformserver.modules.mapper.IssuesMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【issues(缺陷/任务表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class IssuesServiceImpl extends ServiceImpl<IssuesMapper, Issues>
    implements IssuesService{

}





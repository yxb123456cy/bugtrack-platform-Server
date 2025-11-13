package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.IssueHistory;
import org.lemon.bugtrackplatformserver.modules.service.IssueHistoryService;
import org.lemon.bugtrackplatformserver.modules.mapper.IssueHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【issue_history(问题单历史记录表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class IssueHistoryServiceImpl extends ServiceImpl<IssueHistoryMapper, IssueHistory>
    implements IssueHistoryService{

}





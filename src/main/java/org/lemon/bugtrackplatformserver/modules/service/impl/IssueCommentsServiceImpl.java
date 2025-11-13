package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.IssueComments;
import org.lemon.bugtrackplatformserver.modules.service.IssueCommentsService;
import org.lemon.bugtrackplatformserver.modules.mapper.IssueCommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【issue_comments(问题单评论表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class IssueCommentsServiceImpl extends ServiceImpl<IssueCommentsMapper, IssueComments>
    implements IssueCommentsService{

}





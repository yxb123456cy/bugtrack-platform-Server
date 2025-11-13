package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.IssueAttachments;
import org.lemon.bugtrackplatformserver.modules.service.IssueAttachmentsService;
import org.lemon.bugtrackplatformserver.modules.mapper.IssueAttachmentsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【issue_attachments(附件表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class IssueAttachmentsServiceImpl extends ServiceImpl<IssueAttachmentsMapper, IssueAttachments>
    implements IssueAttachmentsService{

}





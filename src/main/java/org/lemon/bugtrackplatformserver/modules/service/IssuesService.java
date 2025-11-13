package org.lemon.bugtrackplatformserver.modules.service;

import org.lemon.bugtrackplatformserver.modules.domain.Issues;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【issues(缺陷/任务表)】的数据库操作Service
* @createDate 2025-11-13 16:52:24
*/
public interface IssuesService extends IService<Issues> {
    Issues createIssue(Issues issue);
}

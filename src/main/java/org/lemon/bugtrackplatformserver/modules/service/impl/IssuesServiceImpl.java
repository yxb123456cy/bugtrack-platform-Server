package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    @Override
    public Issues createIssue(Issues issue) {
        Long projectId = issue.getProjectId();
        Long next = 1L;
        if (projectId != null) {
            QueryWrapper<Issues> qw = new QueryWrapper<>();
            qw.select("max(issue_no) as issue_no").eq("project_id", projectId);
            Issues maxIssue = getOne(qw, false);
            if (maxIssue != null && maxIssue.getIssueNo() != null) {
                next = maxIssue.getIssueNo() + 1;
            }
        }
        issue.setIssueNo(next);
        save(issue);
        return getById(issue.getId());
    }
}





package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.Issues;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssuesCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssuesIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssuesPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssuesUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.IssueResponse;
import org.lemon.bugtrackplatformserver.modules.service.IssuesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/issues")
@Validated
public class IssuesController {

    private final IssuesService issuesService;

    public IssuesController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }

    /**
     * 创建缺陷/任务
     * @param request 创建请求体，包含项目、类型、标题、描述等
     * @return 创建成功后的缺陷信息
     */
    @PostMapping
    @ApiOperationLog(description = "创建缺陷/任务")
    public Response<IssueResponse> create(@Valid @RequestBody IssuesCreateRequest request) {
        Issues issue = Issues.builder()
                .projectId(request.getProjectId())
                .type(request.getType())
                .title(request.getTitle())
                .description(request.getDescription())
                .severity(request.getSeverity())
                .priority(request.getPriority())
                .reporterId(request.getReporterId())
                .assigneeId(request.getAssigneeId())
                .module(request.getModule())
                .status("new")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Issues saved = issuesService.createIssue(issue);
        return Response.success(toResp(saved));
    }

    /**
     * 缺陷详情
     * @param request ID 请求
     * @return 缺陷信息
     */
    @GetMapping("/detail")
    @ApiOperationLog(description = "查询缺陷详情")
    public Response<IssueResponse> detail(@Valid IssuesIdRequest request) {
        Issues issue = issuesService.getById(request.getId());
        return Response.success(toResp(issue));
    }

    /**
     * 分页查询缺陷
     * @param query 分页与过滤参数
     * @return 分页缺陷信息
     */
    @GetMapping
    @ApiOperationLog(description = "分页查询缺陷")
    public Response<Page<IssueResponse>> page(@Valid IssuesPageQueryRequest query) {
        QueryWrapper<Issues> qw = new QueryWrapper<>();
        if (query.getProjectId() != null) {
            qw.eq("project_id", query.getProjectId());
        }
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) {
            qw.and(w -> w.like("title", query.getKeyword())
                    .or().like("description", query.getKeyword()));
        }
        if (query.getType() != null) {
            qw.eq("type", query.getType());
        }
        if (query.getStatus() != null) {
            qw.eq("status", query.getStatus());
        }
        if (query.getSeverity() != null) {
            qw.eq("severity", query.getSeverity());
        }
        if (query.getPriority() != null) {
            qw.eq("priority", query.getPriority());
        }
        if (query.getAssigneeId() != null) {
            qw.eq("assignee_id", query.getAssigneeId());
        }
        qw.orderByDesc("id");
        Page<Issues> page = issuesService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<IssueResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    /**
     * 更新缺陷
     * @param request 更新请求体（包含 ID）
     * @return 更新后的缺陷信息
     */
    @PutMapping("/update")
    @ApiOperationLog(description = "更新缺陷")
    public Response<IssueResponse> update(@Valid @RequestBody IssuesUpdateRequest request) {
        Issues existing = issuesService.getById(request.getId());
        if (existing == null) {
            return Response.success(null);
        }
        if (request.getProjectId() != null) existing.setProjectId(request.getProjectId());
        if (request.getType() != null) existing.setType(request.getType());
        if (request.getTitle() != null) existing.setTitle(request.getTitle());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());
        if (request.getSeverity() != null) existing.setSeverity(request.getSeverity());
        if (request.getPriority() != null) existing.setPriority(request.getPriority());
        if (request.getReporterId() != null) existing.setReporterId(request.getReporterId());
        if (request.getAssigneeId() != null) existing.setAssigneeId(request.getAssigneeId());
        if (request.getModule() != null) existing.setModule(request.getModule());
        if (request.getResolution() != null) existing.setResolution(request.getResolution());
        existing.setUpdatedAt(LocalDateTime.now());
        issuesService.updateById(existing);
        Issues updated = issuesService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    /**
     * 删除缺陷
     * @param request ID 请求
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除缺陷")
    public Response<Boolean> delete(@Valid IssuesIdRequest request) {
        boolean removed = issuesService.removeById(request.getId());
        return Response.success(removed);
    }

    private IssueResponse toResp(Issues i) {
        if (i == null) return null;
        IssueResponse r = new IssueResponse();
        r.setId(i.getId());
        r.setProjectId(i.getProjectId());
        r.setIssueNo(i.getIssueNo());
        r.setType(i.getType());
        r.setTitle(i.getTitle());
        r.setDescription(i.getDescription());
        r.setStatus(i.getStatus());
        r.setSeverity(i.getSeverity());
        r.setPriority(i.getPriority());
        r.setReporterId(i.getReporterId());
        r.setAssigneeId(i.getAssigneeId());
        r.setModule(i.getModule());
        r.setResolution(i.getResolution());
        r.setCreatedAt(i.getCreatedAt());
        r.setUpdatedAt(i.getUpdatedAt());
        return r;
    }
}

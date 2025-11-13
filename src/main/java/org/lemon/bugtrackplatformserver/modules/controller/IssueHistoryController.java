package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.IssueHistory;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueHistoryCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueHistoryPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.IssueHistoryResponse;
import org.lemon.bugtrackplatformserver.modules.service.IssueHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/issue-history")
@Validated
public class IssueHistoryController {

    private final IssueHistoryService issueHistoryService;

    public IssueHistoryController(IssueHistoryService issueHistoryService) {
        this.issueHistoryService = issueHistoryService;
    }

    @PostMapping
    @ApiOperationLog(description = "记录缺陷历史")
    public Response<IssueHistoryResponse> create(@Valid @RequestBody IssueHistoryCreateRequest request) {
        IssueHistory h = IssueHistory.builder()
                .issueId(request.getIssueId())
                .actorId(request.getActorId())
                .action(request.getAction())
                .fromValue(request.getFromValue())
                .toValue(request.getToValue())
                .remark(request.getRemark())
                .createdAt(LocalDateTime.now())
                .build();
        issueHistoryService.save(h);
        IssueHistory saved = issueHistoryService.getById(h.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "历史详情")
    public Response<IssueHistoryResponse> detail(@Valid ProjectsIdRequest request) {
        IssueHistory h = issueHistoryService.getById(request.getId());
        return Response.success(toResp(h));
    }

    @GetMapping("/by-issue")
    @ApiOperationLog(description = "按缺陷查询历史列表")
    public Response<List<IssueHistoryResponse>> listByIssue(@Valid ProjectsIdRequest request) {
        QueryWrapper<IssueHistory> qw = new QueryWrapper<>();
        qw.eq("issue_id", request.getId()).orderByDesc("id");
        List<IssueHistory> list = issueHistoryService.list(qw);
        return Response.success(list.stream().map(this::toResp).toList());
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询历史")
    public Response<Page<IssueHistoryResponse>> page(@Valid IssueHistoryPageQueryRequest query) {
        QueryWrapper<IssueHistory> qw = new QueryWrapper<>();
        if (query.getIssueId() != null) qw.eq("issue_id", query.getIssueId());
        if (query.getActorId() != null) qw.eq("actor_id", query.getActorId());
        if (query.getAction() != null) qw.eq("action", query.getAction());
        qw.orderByDesc("id");
        Page<IssueHistory> page = issueHistoryService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<IssueHistoryResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    private IssueHistoryResponse toResp(IssueHistory h) {
        if (h == null) return null;
        IssueHistoryResponse r = new IssueHistoryResponse();
        r.setId(h.getId());
        r.setIssueId(h.getIssueId());
        r.setActorId(h.getActorId());
        r.setAction(h.getAction());
        r.setFromValue(h.getFromValue());
        r.setToValue(h.getToValue());
        r.setRemark(h.getRemark());
        r.setCreatedAt(h.getCreatedAt());
        return r;
    }
}

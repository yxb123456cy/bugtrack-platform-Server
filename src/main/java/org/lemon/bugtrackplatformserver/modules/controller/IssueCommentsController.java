package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.IssueComments;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueCommentCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueCommentUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueCommentPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.IssueCommentResponse;
import org.lemon.bugtrackplatformserver.modules.service.IssueCommentsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/issue-comments")
@Validated
public class IssueCommentsController {

    private final IssueCommentsService issueCommentsService;

    public IssueCommentsController(IssueCommentsService issueCommentsService) {
        this.issueCommentsService = issueCommentsService;
    }

    @PostMapping
    @ApiOperationLog(description = "新增评论")
    public Response<IssueCommentResponse> create(@Valid @RequestBody IssueCommentCreateRequest request) {
        IssueComments c = IssueComments.builder()
                .issueId(request.getIssueId())
                .userId(request.getUserId())
                .content(request.getContent())
                .mentionedUserIds(request.getMentionedUserIds())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        issueCommentsService.save(c);
        IssueComments saved = issueCommentsService.getById(c.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "评论详情")
    public Response<IssueCommentResponse> detail(@Valid ProjectsIdRequest request) {
        IssueComments c = issueCommentsService.getById(request.getId());
        return Response.success(toResp(c));
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询评论")
    public Response<Page<IssueCommentResponse>> page(@Valid IssueCommentPageQueryRequest query) {
        QueryWrapper<IssueComments> qw = new QueryWrapper<>();
        if (query.getIssueId() != null) qw.eq("issue_id", query.getIssueId());
        if (query.getUserId() != null) qw.eq("user_id", query.getUserId());
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) qw.like("content", query.getKeyword());
        qw.orderByDesc("id");
        Page<IssueComments> page = issueCommentsService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<IssueCommentResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    @PutMapping("/update")
    @ApiOperationLog(description = "更新评论")
    public Response<IssueCommentResponse> update(@Valid @RequestBody IssueCommentUpdateRequest request) {
        IssueComments existing = issueCommentsService.getById(request.getId());
        if (existing == null) return Response.success(null);
        if (request.getContent() != null) existing.setContent(request.getContent());
        if (request.getMentionedUserIds() != null) existing.setMentionedUserIds(request.getMentionedUserIds());
        existing.setUpdatedAt(LocalDateTime.now());
        issueCommentsService.updateById(existing);
        IssueComments updated = issueCommentsService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除评论")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = issueCommentsService.removeById(request.getId());
        return Response.success(removed);
    }

    private IssueCommentResponse toResp(IssueComments c) {
        if (c == null) return null;
        IssueCommentResponse r = new IssueCommentResponse();
        r.setId(c.getId());
        r.setIssueId(c.getIssueId());
        r.setUserId(c.getUserId());
        r.setContent(c.getContent());
        r.setMentionedUserIds(c.getMentionedUserIds());
        r.setCreatedAt(c.getCreatedAt());
        r.setUpdatedAt(c.getUpdatedAt());
        return r;
    }
}

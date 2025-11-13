package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.IssueTags;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueTagsBindRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueTagsUnbindRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.IssueTagResponse;
import org.lemon.bugtrackplatformserver.modules.service.IssueTagsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/issue-tags")
@Validated
public class IssueTagsController {

    private final IssueTagsService issueTagsService;

    public IssueTagsController(IssueTagsService issueTagsService) {
        this.issueTagsService = issueTagsService;
    }

    @PostMapping("/bind")
    @ApiOperationLog(description = "绑定标签到缺陷")
    public Response<IssueTagResponse> bind(@Valid @RequestBody IssueTagsBindRequest request) {
        IssueTags it = IssueTags.builder()
                .issueId(request.getIssueId())
                .tagId(request.getTagId())
                .createdAt(LocalDateTime.now())
                .build();
        issueTagsService.save(it);
        IssueTags saved = issueTagsService.getById(it.getId());
        return Response.success(toResp(saved));
    }

    @DeleteMapping("/unbind")
    @ApiOperationLog(description = "解绑缺陷标签")
    public Response<Boolean> unbind(@Valid @RequestBody IssueTagsUnbindRequest request) {
        boolean removed = issueTagsService.removeById(request.getId());
        return Response.success(removed);
    }

    @GetMapping("/by-issue")
    @ApiOperationLog(description = "查询缺陷的标签列表")
    public Response<List<IssueTagResponse>> listByIssue(@Valid ProjectsIdRequest request) {
        QueryWrapper<IssueTags> qw = new QueryWrapper<>();
        qw.eq("issue_id", request.getId()).orderByDesc("id");
        List<IssueTags> list = issueTagsService.list(qw);
        return Response.success(list.stream().map(this::toResp).toList());
    }

    @GetMapping("/by-tag")
    @ApiOperationLog(description = "查询标签绑定的缺陷列表")
    public Response<Page<IssueTagResponse>> pageByTag(@Valid ProjectsIdRequest request,
                                                      @RequestParam(defaultValue = "1") int pageNum,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<IssueTags> qw = new QueryWrapper<>();
        qw.eq("tag_id", request.getId()).orderByDesc("id");
        Page<IssueTags> page = issueTagsService.page(new Page<>(pageNum, pageSize), qw);
        Page<IssueTagResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    private IssueTagResponse toResp(IssueTags it) {
        if (it == null) return null;
        IssueTagResponse r = new IssueTagResponse();
        r.setId(it.getId());
        r.setIssueId(it.getIssueId());
        r.setTagId(it.getTagId());
        r.setCreatedAt(it.getCreatedAt());
        return r;
    }
}

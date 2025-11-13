package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.IssueAttachments;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueAttachmentCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueAttachmentPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.IssueAttachmentUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.IssueAttachmentResponse;
import org.lemon.bugtrackplatformserver.modules.service.IssueAttachmentsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/issue-attachments")
@Validated
public class IssueAttachmentsController {

    private final IssueAttachmentsService issueAttachmentsService;

    public IssueAttachmentsController(IssueAttachmentsService issueAttachmentsService) {
        this.issueAttachmentsService = issueAttachmentsService;
    }

    @PostMapping
    @ApiOperationLog(description = "新增缺陷附件")
    public Response<IssueAttachmentResponse> create(@Valid @RequestBody IssueAttachmentCreateRequest request) {
        IssueAttachments a = IssueAttachments.builder()
                .issueId(request.getIssueId())
                .uploaderId(request.getUploaderId())
                .fileName(request.getFileName())
                .fileSize(request.getFileSize())
                .storagePath(request.getStoragePath())
                .mimeType(request.getMimeType())
                .createdAt(LocalDateTime.now())
                .build();
        issueAttachmentsService.save(a);
        IssueAttachments saved = issueAttachmentsService.getById(a.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "附件详情")
    public Response<IssueAttachmentResponse> detail(@Valid ProjectsIdRequest request) {
        IssueAttachments a = issueAttachmentsService.getById(request.getId());
        return Response.success(toResp(a));
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询附件")
    public Response<Page<IssueAttachmentResponse>> page(@Valid IssueAttachmentPageQueryRequest query) {
        QueryWrapper<IssueAttachments> qw = new QueryWrapper<>();
        if (query.getIssueId() != null) qw.eq("issue_id", query.getIssueId());
        if (query.getUploaderId() != null) qw.eq("uploader_id", query.getUploaderId());
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) qw.like("file_name", query.getKeyword());
        qw.orderByDesc("id");
        Page<IssueAttachments> page = issueAttachmentsService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<IssueAttachmentResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    @PutMapping("/update")
    @ApiOperationLog(description = "更新附件元数据")
    public Response<IssueAttachmentResponse> update(@Valid @RequestBody IssueAttachmentUpdateRequest request) {
        IssueAttachments existing = issueAttachmentsService.getById(request.getId());
        if (existing == null) return Response.success(null);
        if (request.getFileName() != null) existing.setFileName(request.getFileName());
        if (request.getFileSize() != null) existing.setFileSize(request.getFileSize());
        if (request.getStoragePath() != null) existing.setStoragePath(request.getStoragePath());
        if (request.getMimeType() != null) existing.setMimeType(request.getMimeType());
        issueAttachmentsService.updateById(existing);
        IssueAttachments updated = issueAttachmentsService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除附件记录")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = issueAttachmentsService.removeById(request.getId());
        return Response.success(removed);
    }

    private IssueAttachmentResponse toResp(IssueAttachments a) {
        if (a == null) return null;
        IssueAttachmentResponse r = new IssueAttachmentResponse();
        r.setId(a.getId());
        r.setIssueId(a.getIssueId());
        r.setUploaderId(a.getUploaderId());
        r.setFileName(a.getFileName());
        r.setFileSize(a.getFileSize());
        r.setStoragePath(a.getStoragePath());
        r.setMimeType(a.getMimeType());
        r.setCreatedAt(a.getCreatedAt());
        return r;
    }
}

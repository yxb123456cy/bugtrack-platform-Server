package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.Tags;
import org.lemon.bugtrackplatformserver.modules.dto.request.TagsCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.TagsPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.TagsUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.TagResponse;
import org.lemon.bugtrackplatformserver.modules.service.TagsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tags")
@Validated
public class TagsController {

    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @PostMapping
    @ApiOperationLog(description = "创建标签")
    public Response<TagResponse> create(@Valid @RequestBody TagsCreateRequest request) {
        Tags t = Tags.builder()
                .projectId(request.getProjectId())
                .name(request.getName())
                .color(request.getColor())
                .createdAt(LocalDateTime.now())
                .build();
        tagsService.save(t);
        Tags saved = tagsService.getById(t.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "查询标签详情")
    public Response<TagResponse> detail(@Valid ProjectsIdRequest request) {
        Tags t = tagsService.getById(request.getId());
        return Response.success(toResp(t));
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询标签")
    public Response<Page<TagResponse>> page(@Valid TagsPageQueryRequest query) {
        QueryWrapper<Tags> qw = new QueryWrapper<>();
        if (query.getProjectId() != null) {
            qw.eq("project_id", query.getProjectId());
        }
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) {
            qw.like("name", query.getKeyword());
        }
        qw.orderByDesc("id");
        Page<Tags> page = tagsService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<TagResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    @PutMapping("/update")
    @ApiOperationLog(description = "更新标签")
    public Response<TagResponse> update(@Valid @RequestBody TagsUpdateRequest request) {
        Tags existing = tagsService.getById(request.getId());
        if (existing == null) {
            return Response.success(null);
        }
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getColor() != null) existing.setColor(request.getColor());
        tagsService.updateById(existing);
        Tags updated = tagsService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除标签")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = tagsService.removeById(request.getId());
        return Response.success(removed);
    }

    private TagResponse toResp(Tags t) {
        if (t == null) return null;
        TagResponse r = new TagResponse();
        r.setId(t.getId());
        r.setProjectId(t.getProjectId());
        r.setName(t.getName());
        r.setColor(t.getColor());
        r.setCreatedAt(t.getCreatedAt());
        return r;
    }
}

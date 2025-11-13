-- ================================================================
-- 数据库名：bugtrack_platform
-- 功能说明：轻量级缺陷管理平台（类似 腾讯TApd / Jira 的 MVP 实现）
-- 作者：Sky Takeaway
-- 引擎与编码：InnoDB + utf8mb4_general_ci
-- 注意：本设计为独立表结构版本，不包含外键约束，以便微服务或灵活扩展。
-- ================================================================

SET NAMES utf8mb4;
CREATE DATABASE IF NOT EXISTS `bugtrack_platform`
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci;

USE `bugtrack_platform`;

-- ================================================================
-- 1. 用户表（系统账户信息）
-- ================================================================
CREATE TABLE IF NOT EXISTS `users`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
    `username`      VARCHAR(64)     NOT NULL COMMENT '用户名（唯一）',
    `email`         VARCHAR(128)             DEFAULT NULL COMMENT '邮箱地址（唯一）',
    `display_name`  VARCHAR(128)             DEFAULT NULL COMMENT '显示名称',
    `password_hash` VARCHAR(255)             DEFAULT NULL COMMENT '密码哈希值',
    `status`        TINYINT         NOT NULL DEFAULT 1 COMMENT '状态：0禁用，1启用',
    `created_at`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_users_username` (`username`),
    UNIQUE KEY `ux_users_email` (`email`),
    KEY `idx_users_status` (`status`),
    KEY `idx_users_created_at` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户表';

-- ================================================================
-- 2. 项目表（缺陷与任务的容器）
-- ================================================================
CREATE TABLE IF NOT EXISTS `projects`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    `key`         VARCHAR(32)     NOT NULL COMMENT '项目唯一标识，例如：PROJ1',
    `name`        VARCHAR(128)    NOT NULL COMMENT '项目名称',
    `description` TEXT COMMENT '项目描述',
    `owner_id`    BIGINT UNSIGNED          DEFAULT NULL COMMENT '项目负责人ID',
    `status`      TINYINT         NOT NULL DEFAULT 1 COMMENT '状态：0归档，1活跃',
    `created_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_projects_key` (`key`),
    KEY `idx_projects_owner` (`owner_id`),
    KEY `idx_projects_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='项目表';

-- ================================================================
-- 3. 项目成员表（成员与角色关联）
-- ================================================================
CREATE TABLE IF NOT EXISTS `project_members`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `project_id` BIGINT UNSIGNED NOT NULL COMMENT '所属项目ID',
    `user_id`    BIGINT UNSIGNED NOT NULL COMMENT '成员用户ID',
    `role`       VARCHAR(32)     NOT NULL DEFAULT 'member' COMMENT '角色：owner/admin/member/viewer',
    `joined_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_project_members_proj_user` (`project_id`, `user_id`),
    KEY `idx_project_members_user` (`user_id`),
    KEY `idx_project_members_project` (`project_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='项目成员表';

-- ================================================================
-- 4. 角色表（全局或项目级角色定义）
-- ================================================================
CREATE TABLE IF NOT EXISTS `roles`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`        VARCHAR(64)  NOT NULL COMMENT '角色名称',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '角色描述',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_roles_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色定义表';

-- ================================================================
-- 5. 缺陷/任务表（核心问题单）
-- ================================================================
CREATE TABLE IF NOT EXISTS `issues`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '问题单ID',
    `project_id`  BIGINT UNSIGNED NOT NULL COMMENT '所属项目ID',
    `issue_no`    BIGINT UNSIGNED NOT NULL COMMENT '项目内递增编号',
    `type`        VARCHAR(32)     NOT NULL DEFAULT 'bug' COMMENT '类型：bug/task/feature',
    `title`       VARCHAR(255)    NOT NULL COMMENT '标题',
    `description` TEXT COMMENT '详细描述',
    `status`      VARCHAR(32)     NOT NULL DEFAULT 'new' COMMENT '状态：new/in_progress/resolved/verified/closed',
    `severity`    VARCHAR(16)              DEFAULT 'major' COMMENT '严重程度：blocker/critical/major/minor/trivial',
    `priority`    VARCHAR(8)               DEFAULT 'P2' COMMENT '优先级：P0-P3',
    `reporter_id` BIGINT UNSIGNED          DEFAULT NULL COMMENT '报告人ID',
    `assignee_id` BIGINT UNSIGNED          DEFAULT NULL COMMENT '处理人ID',
    `module`      VARCHAR(128)             DEFAULT NULL COMMENT '所属模块名称',
    `resolution`  VARCHAR(64)              DEFAULT NULL COMMENT '解决方案/结论',
    `created_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_issues_project_issue_no` (`project_id`, `issue_no`),
    KEY `idx_issues_project` (`project_id`),
    KEY `idx_issues_status` (`status`),
    KEY `idx_issues_assignee` (`assignee_id`),
    KEY `idx_issues_reporter` (`reporter_id`),
    KEY `idx_issues_created_at` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='缺陷/任务表';

-- ================================================================
-- 6. 项目问题编号序列表（用于生成 issue_no）
-- ================================================================
CREATE TABLE IF NOT EXISTS `issue_sequence`
(
    `project_id` BIGINT UNSIGNED NOT NULL PRIMARY KEY COMMENT '项目ID',
    `next_no`    BIGINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '下一个可用编号',
    `updated_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='项目问题编号序列表';

-- ================================================================
-- 7. 评论表（问题单讨论区）
-- ================================================================
CREATE TABLE IF NOT EXISTS `issue_comments`
(
    `id`                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `issue_id`           BIGINT UNSIGNED NOT NULL COMMENT '所属问题单ID',
    `user_id`            BIGINT UNSIGNED NOT NULL COMMENT '评论人ID',
    `content`            TEXT            NOT NULL COMMENT '评论内容',
    `mentioned_user_ids` VARCHAR(255)             DEFAULT NULL COMMENT '被@的用户ID（逗号分隔）',
    `created_at`         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_issue_comments_issue` (`issue_id`),
    KEY `idx_issue_comments_user` (`user_id`),
    KEY `idx_issue_comments_created_at` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='问题单评论表';

-- ================================================================
-- 8. 附件表（关联 MinIO 文件）
-- ================================================================
CREATE TABLE IF NOT EXISTS `issue_attachments`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '附件ID',
    `issue_id`     BIGINT UNSIGNED NOT NULL COMMENT '所属问题单ID',
    `uploader_id`  BIGINT UNSIGNED          DEFAULT NULL COMMENT '上传者ID',
    `file_name`    VARCHAR(255)    NOT NULL COMMENT '文件名',
    `file_size`    BIGINT UNSIGNED          DEFAULT NULL COMMENT '文件大小（字节）',
    `storage_path` VARCHAR(512)    NOT NULL COMMENT 'MinIO 对象路径或本地路径',
    `mime_type`    VARCHAR(128)             DEFAULT NULL COMMENT '文件类型',
    `created_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    PRIMARY KEY (`id`),
    KEY `idx_issue_attachments_issue` (`issue_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='附件表';

-- ================================================================
-- 9. 历史记录表（状态流转与操作记录）
-- ================================================================
CREATE TABLE IF NOT EXISTS `issue_history`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '历史记录ID',
    `issue_id`   BIGINT UNSIGNED NOT NULL COMMENT '问题单ID',
    `actor_id`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '操作人ID',
    `action`     VARCHAR(64)     NOT NULL COMMENT '操作类型：status_change/assign/comment/edit/attach',
    `from_value` VARCHAR(255)             DEFAULT NULL COMMENT '变更前值',
    `to_value`   VARCHAR(255)             DEFAULT NULL COMMENT '变更后值',
    `remark`     TEXT COMMENT '备注信息',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_issue_history_issue` (`issue_id`),
    KEY `idx_issue_history_actor` (`actor_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='问题单历史记录表';

-- ================================================================
-- 10. 标签表（Issue 标签定义）
-- ================================================================
CREATE TABLE IF NOT EXISTS `tags`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    `project_id` BIGINT UNSIGNED          DEFAULT NULL COMMENT '所属项目ID',
    `name`       VARCHAR(64)     NOT NULL COMMENT '标签名',
    `color`      VARCHAR(16)              DEFAULT NULL COMMENT '标签颜色（HEX）',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_tags_project_name` (`project_id`, `name`),
    KEY `idx_tags_project` (`project_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='标签表';

-- ================================================================
-- 11. 问题标签关联表（多对多关系）
-- ================================================================
CREATE TABLE IF NOT EXISTS `issue_tags`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `issue_id`   BIGINT UNSIGNED NOT NULL COMMENT '问题单ID',
    `tag_id`     BIGINT UNSIGNED NOT NULL COMMENT '标签ID',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_issue_tags_issue_tag` (`issue_id`, `tag_id`),
    KEY `idx_issue_tags_issue` (`issue_id`),
    KEY `idx_issue_tags_tag` (`tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='问题标签关联表';

-- ================================================================
-- 12. 通知表（系统通知 / 消息）
-- ================================================================
CREATE TABLE IF NOT EXISTS `notifications`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    `user_id`    BIGINT UNSIGNED NOT NULL COMMENT '接收用户ID',
    `project_id` BIGINT UNSIGNED          DEFAULT NULL COMMENT '相关项目ID',
    `type`       VARCHAR(64)     NOT NULL COMMENT '通知类型：issue_assigned/comment_mentioned 等',
    `target_id`  BIGINT UNSIGNED          DEFAULT NULL COMMENT '关联目标ID（如issue_id）',
    `content`    VARCHAR(512)             DEFAULT NULL COMMENT '通知内容',
    `is_read`    TINYINT         NOT NULL DEFAULT 0 COMMENT '是否已读：0未读，1已读',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_notifications_user` (`user_id`),
    KEY `idx_notifications_project` (`project_id`),
    KEY `idx_notifications_is_read` (`is_read`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='系统通知表';

-- ================================================================
-- 13. Token 表（API 令牌或刷新会话）
-- ================================================================
CREATE TABLE IF NOT EXISTS `auth_tokens`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Token ID',
    `user_id`    BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `token`      VARCHAR(255)    NOT NULL COMMENT '令牌字符串',
    `device`     VARCHAR(128)             DEFAULT NULL COMMENT '登录设备标识',
    `expired_at` DATETIME                 DEFAULT NULL COMMENT '过期时间',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_auth_tokens_token` (`token`),
    KEY `idx_auth_tokens_user` (`user_id`),
    KEY `idx_auth_tokens_expired_at` (`expired_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='Token 存储表';

-- ================================================================
-- 14. 系统操作日志表（审计与调试用）
-- ================================================================
CREATE TABLE IF NOT EXISTS `system_logs`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `actor_id`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '操作者ID',
    `level`      VARCHAR(16)              DEFAULT 'info' COMMENT '日志级别：info/warn/error',
    `category`   VARCHAR(64)              DEFAULT NULL COMMENT '日志分类',
    `message`    TEXT COMMENT '日志内容',
    `meta`       JSON                     DEFAULT NULL COMMENT '附加元数据(JSON)',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_system_logs_actor` (`actor_id`),
    KEY `idx_system_logs_level` (`level`),
    KEY `idx_system_logs_created` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='系统日志表';

-- ================================================================
-- 15. 项目配置表（键值对配置）
-- ================================================================
CREATE TABLE IF NOT EXISTS `settings`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `project_id` BIGINT UNSIGNED          DEFAULT NULL COMMENT '项目ID',
    `key`        VARCHAR(128)    NOT NULL COMMENT '配置键',
    `value`      TEXT COMMENT '配置值',
    `updated_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_settings_proj_key` (`project_id`, `key`),
    KEY `idx_settings_project` (`project_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='项目配置表';

-- ================================================================
-- 初始化基础角色数据
-- ================================================================
INSERT INTO `roles` (`name`, `description`)
VALUES ('owner', '项目拥有者'),
       ('admin', '项目管理员'),
       ('member', '项目成员'),
       ('viewer', '只读成员');
-- ================================================================
-- 0. 新增：空间表（团队/组织级容器）
-- ================================================================
CREATE TABLE IF NOT EXISTS `spaces`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '空间ID',
    `name`        VARCHAR(128)    NOT NULL COMMENT '空间名称',
    `key`         VARCHAR(32)     NOT NULL COMMENT '空间唯一标识，如 TEAM_A',
    `owner_id`    BIGINT UNSIGNED NOT NULL COMMENT '空间创建者ID',
    `description` TEXT COMMENT '空间描述',
    `visibility`  TINYINT         NOT NULL DEFAULT 1 COMMENT '可见性：0私有，1团队可见，2公开',
    `status`      TINYINT         NOT NULL DEFAULT 1 COMMENT '状态：0禁用，1启用',
    `created_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_spaces_key` (`key`),
    KEY `idx_spaces_owner` (`owner_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='空间表（多租户/团队维度）';


-- ================================================================
-- 0.1 空间成员表（成员与角色）
-- ================================================================
CREATE TABLE IF NOT EXISTS `space_members`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `space_id`  BIGINT UNSIGNED NOT NULL COMMENT '空间ID',
    `user_id`   BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `role`      VARCHAR(32)     NOT NULL DEFAULT 'member' COMMENT '角色：owner/admin/member/viewer',
    `joined_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_space_members_space_user` (`space_id`, `user_id`),
    KEY `idx_space_members_user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='空间成员表';


-- ================================================================
-- 2. 更新：项目表（添加 space_id 维度）
-- ================================================================
ALTER TABLE `projects`
    ADD COLUMN `space_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '所属空间ID' AFTER `id`,
    ADD KEY `idx_projects_space` (`space_id`);


-- ================================================================
-- 2.1 新表：项目模板表（可选，用于创建默认配置）
-- ================================================================
CREATE TABLE IF NOT EXISTS `project_templates`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `space_id`    BIGINT UNSIGNED          DEFAULT NULL COMMENT '所属空间ID',
    `name`        VARCHAR(128)    NOT NULL COMMENT '模板名称',
    `description` TEXT COMMENT '模板描述',
    `is_default`  TINYINT         NOT NULL DEFAULT 0 COMMENT '是否为默认模板',
    `config_json` JSON COMMENT '默认配置内容（如状态流、字段模板）',
    `created_at`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_proj_templates_name` (`space_id`, `name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='项目模板表';


-- ================================================================
-- 更新：系统日志表扩展 (记录空间与项目维度)
-- ================================================================
ALTER TABLE `system_logs`
    ADD COLUMN `space_id`   BIGINT UNSIGNED DEFAULT NULL COMMENT '空间ID',
    ADD COLUMN `project_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '项目ID',
    ADD KEY `idx_system_logs_space` (`space_id`),
    ADD KEY `idx_system_logs_project` (`project_id`);
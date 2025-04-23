package com.oneself.controller;

import com.oneself.model.enums.ChatTypeEnum;
import com.oneself.model.vo.ChatHistoryVO;
import com.oneself.model.vo.ResponseVO;
import com.oneself.service.ChatHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liuhuan
 * date 2025/4/18
 * packageName com.oneself.controller
 * className ChatHistoryController
 * description 对话历史接口
 * version 1.0
 */
@Tag(name = "对话历史")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat/history")
public class ChatHistoryController {
    private final ChatHistoryService chatHistoryService;

    private final ChatMemory chatMemory;

    @Operation(summary = "根据对话类型获取历史列表")
    @GetMapping
    public ResponseVO<List<ChatHistoryVO>> getChatHistoryList(@Schema(description = "会话类型", requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam @NotNull ChatTypeEnum chatType) {
        return ResponseVO.success(chatHistoryService.getChatHistoryList(chatType));
    }

    @Operation(summary = "归档对话")
    @PostMapping("/archive")
    public ResponseVO<Boolean> archive(@Schema(description = "会话 ID", requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam @NotNull String conversationId) {
        return ResponseVO.success(chatHistoryService.archive(conversationId));
    }

    @Operation(summary = "获取对话消息")
    @GetMapping("/messages")
    public ResponseVO<List<Message>> getChatMessageByConversationId(@Schema(description = "会话 ID", requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam @NotNull String conversationId) {
        return ResponseVO.success(chatMemory.get(conversationId, Integer.MAX_VALUE));
    }
}

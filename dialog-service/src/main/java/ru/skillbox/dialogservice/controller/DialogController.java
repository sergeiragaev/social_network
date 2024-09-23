package ru.skillbox.dialogservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.commonlib.dto.statistics.CountDto;
import ru.skillbox.dialogservice.model.dto.DialogDto;
import ru.skillbox.dialogservice.model.dto.MessageDto;
import ru.skillbox.dialogservice.service.DialogService;
import ru.skillbox.dialogservice.service.MessageService;


@RestController
@RequiredArgsConstructor
@RequestMapping("${app.apiPrefix}" + "/dialogs")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Dialog Controller", description = "Dialog API")
public class DialogController {
    private final DialogService dialogService;
    private final MessageService messageService;

    @PutMapping("/{id}")
    public DialogDto updateDialog(
            @PathVariable Long id,
            HttpServletRequest request) {
        long currentAuthUserId = Long.parseLong(request.getParameter("id"));
        return dialogService.updateDialog(currentAuthUserId, id);
    }

    @GetMapping
    @Operation(summary = "Get dialogs")
    public Page<DialogDto> getDialogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "unreadCount,desc") String sort,
            HttpServletRequest request) {
        long currentAuthUserId = Long.parseLong(request.getParameter("id"));
        return dialogService.getDialogs(page, sort, currentAuthUserId);
    }

    @GetMapping("/unread")
    @Operation(summary = "Get Unread")
    public CountDto getUnread(
            HttpServletRequest request) {
        long currentAuthUserId = Long.parseLong(request.getParameter("id"));
        return messageService.getUnread(currentAuthUserId);
    }

    @GetMapping("/messages")
    @Operation(summary = "Get messages")
    public Page<MessageDto> getMessages(
            @RequestParam Long recipientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "time,asc") String sort,
            HttpServletRequest request) {
        long currentAuthUserId = Long.parseLong(request.getParameter("id"));
        return messageService.getMessages(currentAuthUserId, recipientId, page, sort);
    }

    @GetMapping("/recipientId/{id}")
    @Operation(summary = "Get dialog")
    public DialogDto getDialog(
            @PathVariable long id,
            HttpServletRequest request) {
        long currentAuthUserId = Long.parseLong(request.getParameter("id"));
        return dialogService.getDialog(currentAuthUserId, id);
    }
}

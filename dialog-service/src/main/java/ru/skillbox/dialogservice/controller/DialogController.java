package ru.skillbox.dialogservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
            @PathVariable Long id) {
        return dialogService.updateDialog(id);
    }

    @GetMapping
    @Operation(summary = "Get dialogs")
    public Page<DialogDto> getDialogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "unreadCount,desc") String sort) {
        return dialogService.getDialogs(page, sort);
    }

    @GetMapping("/unread")
    @Operation(summary = "Get Unread")
    public CountDto getUnread() {
        return messageService.getUnread();
    }

    @GetMapping("/messages")
    @Operation(summary = "Get messages")
    public Page<MessageDto> getMessages(
            @RequestParam Long recipientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "time,asc") String sort) {
        return messageService.getMessages(recipientId, page, sort);
    }

    @GetMapping("/recipientId/{id}")
    @Operation(summary = "Get dialog")
    public DialogDto getDialog(
            @PathVariable long id) {
        return dialogService.getDialog(id);
    }
}

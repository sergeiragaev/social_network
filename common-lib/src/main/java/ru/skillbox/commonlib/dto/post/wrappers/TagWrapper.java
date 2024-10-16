package ru.skillbox.commonlib.dto.post.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagWrapper {
    private String name;
}

package ru.skillbox.commonlib.util;

import org.springframework.data.domain.Sort;

import java.util.List;

public class SortCreatorUtil {
    private static final int DIRECTION_SLOT = 1;
    private static final int COLUMN_SLOT = 0;
    public static Sort createSort(List<String> sort) {
        return Sort.by(Sort.Direction.fromString(sort.get(DIRECTION_SLOT)), sort.get(COLUMN_SLOT));
    }
    private SortCreatorUtil() {

    }
}

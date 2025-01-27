package com.kolown.porring.board.enums;

// 반응 타입을 위한 Enum
public enum ReactionType {
    LIKE("like"),
    COMMENT("comment");  // 필요한 타입들 추가 가능

    private final String value;

    ReactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
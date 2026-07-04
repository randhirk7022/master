package com.schoolapp.parent.dto;

import com.schoolapp.parent.entity.RelationType;
import jakarta.validation.constraints.NotNull;

public class LinkStudentParentRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long parentId;

    @NotNull
    private RelationType relationType;

    public Long getStudentId() {
        return studentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public RelationType getRelationType() {
        return relationType;
    }
}
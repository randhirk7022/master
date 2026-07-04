package com.schoolapp.parent.dto;

import com.schoolapp.parent.entity.RelationType;

public class StudentParentResponse {

    private Long id;
    private Long studentId;
    private Long parentId;
    private RelationType relationType;

    public StudentParentResponse(Long id, Long studentId, Long parentId, RelationType relationType) {
        this.id = id;
        this.studentId = studentId;
        this.parentId = parentId;
        this.relationType = relationType;
    }

    public Long getId() {
        return id;
    }

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
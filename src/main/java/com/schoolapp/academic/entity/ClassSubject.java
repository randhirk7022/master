package com.schoolapp.academic.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "class_subjects",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"class_id", "subject_id"})
        }
)
public class ClassSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    public Long getId() {
        return id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
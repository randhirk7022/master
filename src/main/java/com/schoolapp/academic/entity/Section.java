package com.schoolapp.academic.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "sections",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "class_id"})
        }
)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
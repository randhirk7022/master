package com.schoolapp.academic.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "school_classes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "academic_year_id"})
        }
)
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "academic_year_id", nullable = false)
    private Long academicYearId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }
}
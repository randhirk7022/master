package com.schoolapp.exam.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.exam.dto.ExamRequests.*;
import com.schoolapp.exam.dto.ExamResponses.*;
import com.schoolapp.exam.entity.*;
import com.schoolapp.exam.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamSubjectRepository examSubjectRepository;
    private final MarkRepository markRepository;

    public ExamService(
            ExamRepository examRepository,
            ExamSubjectRepository examSubjectRepository,
            MarkRepository markRepository
    ) {
        this.examRepository = examRepository;
        this.examSubjectRepository = examSubjectRepository;
        this.markRepository = markRepository;
    }

    public ExamResponse createExam(CreateExamRequest request) {
        Exam exam = new Exam();
        exam.setName(request.getName());
        exam.setAcademicYearId(request.getAcademicYearId());
        exam.setClassId(request.getClassId());
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());

        return toExamResponse(examRepository.save(exam));
    }

    public List<ExamResponse> getExams(Long classId, Long academicYearId) {
        if (classId != null) {
            return examRepository.findByClassId(classId).stream().map(this::toExamResponse).toList();
        }

        if (academicYearId != null) {
            return examRepository.findByAcademicYearId(academicYearId).stream().map(this::toExamResponse).toList();
        }

        return examRepository.findAll().stream().map(this::toExamResponse).toList();
    }

    public ExamResponse getExamById(Long id) {
        return toExamResponse(findExam(id));
    }

    public ExamSubjectResponse addSubjectToExam(Long examId, AddExamSubjectRequest request) {
        findExam(examId);

        if (examSubjectRepository.existsByExamIdAndSubjectId(examId, request.getSubjectId())) {
            throw new RuntimeException("Subject already added to this exam");
        }

        if (request.getPassingMarks().compareTo(request.getMaxMarks()) > 0) {
            throw new RuntimeException("Passing marks cannot be greater than maximum marks");
        }

        ExamSubject examSubject = new ExamSubject();
        examSubject.setExamId(examId);
        examSubject.setSubjectId(request.getSubjectId());
        examSubject.setMaxMarks(request.getMaxMarks());
        examSubject.setPassingMarks(request.getPassingMarks());

        return toExamSubjectResponse(examSubjectRepository.save(examSubject));
    }

    public List<ExamSubjectResponse> getExamSubjects(Long examId) {
        findExam(examId);
        return examSubjectRepository.findByExamId(examId)
                .stream()
                .map(this::toExamSubjectResponse)
                .toList();
    }

    public List<MarkResponse> enterMarks(Long examId, EnterMarksRequest request) {
        Exam exam = findExam(examId);

        if (exam.getStatus() == ExamStatus.PUBLISHED) {
            throw new RuntimeException("Cannot update marks after exam is published");
        }

        ExamSubject examSubject = examSubjectRepository
                .findByExamIdAndSubjectId(examId, request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam subject not found"));

        List<Mark> marks = request.getRecords().stream().map(record -> {
            if (record.getMarksObtained().compareTo(examSubject.getMaxMarks()) > 0) {
                throw new RuntimeException("Marks cannot exceed maximum marks");
            }

            Mark mark = markRepository
                    .findByExamIdAndSubjectIdAndStudentId(examId, request.getSubjectId(), record.getStudentId())
                    .orElseGet(Mark::new);

            mark.setExamId(examId);
            mark.setSubjectId(request.getSubjectId());
            mark.setStudentId(record.getStudentId());
            mark.setMarksObtained(record.getMarksObtained());
            mark.setGrade(calculateGrade(record.getMarksObtained(), examSubject.getMaxMarks()));
            mark.setRemarks(record.getRemarks());

            return mark;
        }).toList();

        return markRepository.saveAll(marks).stream().map(this::toMarkResponse).toList();
    }

    public ExamResponse publishExam(Long examId) {
        Exam exam = findExam(examId);
        exam.setStatus(ExamStatus.PUBLISHED);
        return toExamResponse(examRepository.save(exam));
    }

    public StudentResultResponse getStudentResults(Long studentId) {
        List<MarkResponse> marks = markRepository.findByStudentId(studentId)
                .stream()
                .map(this::toMarkResponse)
                .toList();

        return new StudentResultResponse(studentId, marks);
    }

    private Exam findExam(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
    }

    private String calculateGrade(BigDecimal marksObtained, BigDecimal maxMarks) {
        if (maxMarks.compareTo(BigDecimal.ZERO) == 0) {
            return "F";
        }

        BigDecimal percentage = marksObtained
                .multiply(BigDecimal.valueOf(100))
                .divide(maxMarks, 2, RoundingMode.HALF_UP);

        if (percentage.compareTo(BigDecimal.valueOf(90)) >= 0) return "A+";
        if (percentage.compareTo(BigDecimal.valueOf(80)) >= 0) return "A";
        if (percentage.compareTo(BigDecimal.valueOf(70)) >= 0) return "B";
        if (percentage.compareTo(BigDecimal.valueOf(60)) >= 0) return "C";
        if (percentage.compareTo(BigDecimal.valueOf(50)) >= 0) return "D";
        return "F";
    }

    private ExamResponse toExamResponse(Exam exam) {
        return new ExamResponse(
                exam.getId(),
                exam.getName(),
                exam.getAcademicYearId(),
                exam.getClassId(),
                exam.getStartDate(),
                exam.getEndDate(),
                exam.getStatus()
        );
    }

    private ExamSubjectResponse toExamSubjectResponse(ExamSubject examSubject) {
        return new ExamSubjectResponse(
                examSubject.getId(),
                examSubject.getExamId(),
                examSubject.getSubjectId(),
                examSubject.getMaxMarks(),
                examSubject.getPassingMarks()
        );
    }

    private MarkResponse toMarkResponse(Mark mark) {
        return new MarkResponse(
                mark.getId(),
                mark.getExamId(),
                mark.getSubjectId(),
                mark.getStudentId(),
                mark.getMarksObtained(),
                mark.getGrade(),
                mark.getRemarks()
        );
    }
}
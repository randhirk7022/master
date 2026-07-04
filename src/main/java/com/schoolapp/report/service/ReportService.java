package com.schoolapp.report.service;

import com.schoolapp.attendance.entity.AttendanceStatus;
import com.schoolapp.attendance.repository.AttendanceRepository;
import com.schoolapp.exam.entity.ExamStatus;
import com.schoolapp.exam.repository.ExamRepository;
import com.schoolapp.exam.repository.MarkRepository;
import com.schoolapp.fee.entity.Invoice;
import com.schoolapp.fee.entity.InvoiceStatus;
import com.schoolapp.fee.repository.InvoiceRepository;
import com.schoolapp.liveclass.entity.LiveClassStatus;
import com.schoolapp.liveclass.repository.LiveClassParticipantRepository;
import com.schoolapp.liveclass.repository.LiveClassRepository;
import com.schoolapp.payment.entity.PaymentStatus;
import com.schoolapp.payment.repository.PaymentOrderRepository;
import com.schoolapp.recording.entity.RecordedClassStatus;
import com.schoolapp.recording.repository.RecordedClassRepository;
import com.schoolapp.recording.repository.RecordingAccessLogRepository;
import com.schoolapp.recording.repository.StudentRecordingProgressRepository;
import com.schoolapp.report.dto.ReportResponses.*;
import com.schoolapp.student.entity.StudentStatus;
import com.schoolapp.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReportService {

    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final ExamRepository examRepository;
    private final MarkRepository markRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final LiveClassRepository liveClassRepository;
    private final LiveClassParticipantRepository liveClassParticipantRepository;
    private final RecordedClassRepository recordedClassRepository;
    private final RecordingAccessLogRepository recordingAccessLogRepository;
    private final StudentRecordingProgressRepository studentRecordingProgressRepository;

    public ReportService(StudentRepository studentRepository,
                         AttendanceRepository attendanceRepository,
                         ExamRepository examRepository,
                         MarkRepository markRepository,
                         InvoiceRepository invoiceRepository,
                         PaymentOrderRepository paymentOrderRepository,
                         LiveClassRepository liveClassRepository,
                         LiveClassParticipantRepository liveClassParticipantRepository,
                         RecordedClassRepository recordedClassRepository,
                         RecordingAccessLogRepository recordingAccessLogRepository,
                         StudentRecordingProgressRepository studentRecordingProgressRepository) {
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
        this.examRepository = examRepository;
        this.markRepository = markRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentOrderRepository = paymentOrderRepository;
        this.liveClassRepository = liveClassRepository;
        this.liveClassParticipantRepository = liveClassParticipantRepository;
        this.recordedClassRepository = recordedClassRepository;
        this.recordingAccessLogRepository = recordingAccessLogRepository;
        this.studentRecordingProgressRepository = studentRecordingProgressRepository;
    }

    public StudentReportResponse getStudentReport() {
        long totalStudents = studentRepository.count();
        long activeStudents = studentRepository.findByStatus(StudentStatus.ACTIVE).size();
        long inactiveStudents = studentRepository.findByStatus(StudentStatus.INACTIVE).size();

        return new StudentReportResponse(totalStudents, activeStudents, inactiveStudents);
    }

    public AttendanceReportResponse getAttendanceReport() {
        long totalRecords = attendanceRepository.count();

        long presentCount = attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.PRESENT)
                .count();

        long absentCount = attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.ABSENT)
                .count();

        long lateCount = attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.LATE)
                .count();

        long leaveCount = attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.LEAVE)
                .count();

        return new AttendanceReportResponse(totalRecords, presentCount, absentCount, lateCount, leaveCount);
    }

    public ExamReportResponse getExamReport() {
        long totalExams = examRepository.count();

        long draftExams = examRepository.findAll().stream()
                .filter(exam -> exam.getStatus() == ExamStatus.DRAFT)
                .count();

        long publishedExams = examRepository.findAll().stream()
                .filter(exam -> exam.getStatus() == ExamStatus.PUBLISHED)
                .count();

        long totalMarksRecords = markRepository.count();

        return new ExamReportResponse(totalExams, draftExams, publishedExams, totalMarksRecords);
    }

    public FeeReportResponse getFeeReport() {
        var invoices = invoiceRepository.findAll();

        long totalInvoices = invoices.size();

        long paidInvoices = invoices.stream()
                .filter(invoice -> invoice.getStatus() == InvoiceStatus.PAID)
                .count();

        long unpaidInvoices = invoices.stream()
                .filter(invoice -> invoice.getStatus() == InvoiceStatus.UNPAID)
                .count();

        long partiallyPaidInvoices = invoices.stream()
                .filter(invoice -> invoice.getStatus() == InvoiceStatus.PARTIALLY_PAID)
                .count();

        BigDecimal totalAmount = invoices.stream()
                .map(Invoice::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal paidAmount = invoices.stream()
                .map(Invoice::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal dueAmount = invoices.stream()
                .map(Invoice::getDueAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FeeReportResponse(
                totalInvoices,
                paidInvoices,
                unpaidInvoices,
                partiallyPaidInvoices,
                totalAmount,
                paidAmount,
                dueAmount
        );
    }

    public PaymentReportResponse getPaymentReport() {
        var orders = paymentOrderRepository.findAll();

        long totalOrders = orders.size();

        long successfulOrders = orders.stream()
                .filter(order -> order.getStatus() == PaymentStatus.SUCCESS)
                .count();

        long pendingOrders = orders.stream()
                .filter(order -> order.getStatus() == PaymentStatus.CREATED || order.getStatus() == PaymentStatus.PENDING)
                .count();

        long failedOrders = orders.stream()
                .filter(order -> order.getStatus() == PaymentStatus.FAILED)
                .count();

        BigDecimal successfulAmount = orders.stream()
                .filter(order -> order.getStatus() == PaymentStatus.SUCCESS)
                .map(order -> order.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PaymentReportResponse(
                totalOrders,
                successfulOrders,
                pendingOrders,
                failedOrders,
                successfulAmount
        );
    }

    public LiveClassReportResponse getLiveClassReport() {
        var liveClasses = liveClassRepository.findAll();

        long totalLiveClasses = liveClasses.size();

        long scheduledClasses = liveClasses.stream()
                .filter(liveClass -> liveClass.getStatus() == LiveClassStatus.SCHEDULED)
                .count();

        long completedClasses = liveClasses.stream()
                .filter(liveClass -> liveClass.getStatus() == LiveClassStatus.COMPLETED)
                .count();

        long cancelledClasses = liveClasses.stream()
                .filter(liveClass -> liveClass.getStatus() == LiveClassStatus.CANCELLED)
                .count();

        long totalParticipants = liveClassParticipantRepository.count();

        return new LiveClassReportResponse(
                totalLiveClasses,
                scheduledClasses,
                completedClasses,
                cancelledClasses,
                totalParticipants
        );
    }

    public RecordedClassReportResponse getRecordedClassReport() {
        var recordedClasses = recordedClassRepository.findAll();

        long totalRecordedClasses = recordedClasses.size();

        long availableRecordings = recordedClasses.stream()
                .filter(recordedClass -> recordedClass.getStatus() == RecordedClassStatus.AVAILABLE)
                .count();

        long expiredRecordings = recordedClasses.stream()
                .filter(recordedClass -> recordedClass.getStatus() == RecordedClassStatus.EXPIRED)
                .count();

        long deletedRecordings = recordedClasses.stream()
                .filter(recordedClass -> recordedClass.getStatus() == RecordedClassStatus.DELETED)
                .count();

        long totalAccessLogs = recordingAccessLogRepository.count();
        long totalProgressRecords = studentRecordingProgressRepository.count();

        return new RecordedClassReportResponse(
                totalRecordedClasses,
                availableRecordings,
                expiredRecordings,
                deletedRecordings,
                totalAccessLogs,
                totalProgressRecords
        );
    }
}
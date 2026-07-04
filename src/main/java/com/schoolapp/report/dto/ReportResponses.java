package com.schoolapp.report.dto;

import java.math.BigDecimal;

public class ReportResponses {

    public static class StudentReportResponse {
        private long totalStudents;
        private long activeStudents;
        private long inactiveStudents;

        public StudentReportResponse(long totalStudents, long activeStudents, long inactiveStudents) {
            this.totalStudents = totalStudents;
            this.activeStudents = activeStudents;
            this.inactiveStudents = inactiveStudents;
        }

        public long getTotalStudents() { return totalStudents; }
        public long getActiveStudents() { return activeStudents; }
        public long getInactiveStudents() { return inactiveStudents; }
    }

    public static class AttendanceReportResponse {
        private long totalRecords;
        private long presentCount;
        private long absentCount;
        private long lateCount;
        private long leaveCount;

        public AttendanceReportResponse(long totalRecords, long presentCount, long absentCount,
                                        long lateCount, long leaveCount) {
            this.totalRecords = totalRecords;
            this.presentCount = presentCount;
            this.absentCount = absentCount;
            this.lateCount = lateCount;
            this.leaveCount = leaveCount;
        }

        public long getTotalRecords() { return totalRecords; }
        public long getPresentCount() { return presentCount; }
        public long getAbsentCount() { return absentCount; }
        public long getLateCount() { return lateCount; }
        public long getLeaveCount() { return leaveCount; }
    }

    public static class ExamReportResponse {
        private long totalExams;
        private long draftExams;
        private long publishedExams;
        private long totalMarksRecords;

        public ExamReportResponse(long totalExams, long draftExams, long publishedExams, long totalMarksRecords) {
            this.totalExams = totalExams;
            this.draftExams = draftExams;
            this.publishedExams = publishedExams;
            this.totalMarksRecords = totalMarksRecords;
        }

        public long getTotalExams() { return totalExams; }
        public long getDraftExams() { return draftExams; }
        public long getPublishedExams() { return publishedExams; }
        public long getTotalMarksRecords() { return totalMarksRecords; }
    }

    public static class FeeReportResponse {
        private long totalInvoices;
        private long paidInvoices;
        private long unpaidInvoices;
        private long partiallyPaidInvoices;
        private BigDecimal totalAmount;
        private BigDecimal paidAmount;
        private BigDecimal dueAmount;

        public FeeReportResponse(long totalInvoices, long paidInvoices, long unpaidInvoices,
                                 long partiallyPaidInvoices, BigDecimal totalAmount,
                                 BigDecimal paidAmount, BigDecimal dueAmount) {
            this.totalInvoices = totalInvoices;
            this.paidInvoices = paidInvoices;
            this.unpaidInvoices = unpaidInvoices;
            this.partiallyPaidInvoices = partiallyPaidInvoices;
            this.totalAmount = totalAmount;
            this.paidAmount = paidAmount;
            this.dueAmount = dueAmount;
        }

        public long getTotalInvoices() { return totalInvoices; }
        public long getPaidInvoices() { return paidInvoices; }
        public long getUnpaidInvoices() { return unpaidInvoices; }
        public long getPartiallyPaidInvoices() { return partiallyPaidInvoices; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public BigDecimal getPaidAmount() { return paidAmount; }
        public BigDecimal getDueAmount() { return dueAmount; }
    }

    public static class PaymentReportResponse {
        private long totalOrders;
        private long successfulOrders;
        private long pendingOrders;
        private long failedOrders;
        private BigDecimal successfulAmount;

        public PaymentReportResponse(long totalOrders, long successfulOrders, long pendingOrders,
                                     long failedOrders, BigDecimal successfulAmount) {
            this.totalOrders = totalOrders;
            this.successfulOrders = successfulOrders;
            this.pendingOrders = pendingOrders;
            this.failedOrders = failedOrders;
            this.successfulAmount = successfulAmount;
        }

        public long getTotalOrders() { return totalOrders; }
        public long getSuccessfulOrders() { return successfulOrders; }
        public long getPendingOrders() { return pendingOrders; }
        public long getFailedOrders() { return failedOrders; }
        public BigDecimal getSuccessfulAmount() { return successfulAmount; }
    }

    public static class LiveClassReportResponse {
        private long totalLiveClasses;
        private long scheduledClasses;
        private long completedClasses;
        private long cancelledClasses;
        private long totalParticipants;

        public LiveClassReportResponse(long totalLiveClasses, long scheduledClasses,
                                       long completedClasses, long cancelledClasses,
                                       long totalParticipants) {
            this.totalLiveClasses = totalLiveClasses;
            this.scheduledClasses = scheduledClasses;
            this.completedClasses = completedClasses;
            this.cancelledClasses = cancelledClasses;
            this.totalParticipants = totalParticipants;
        }

        public long getTotalLiveClasses() { return totalLiveClasses; }
        public long getScheduledClasses() { return scheduledClasses; }
        public long getCompletedClasses() { return completedClasses; }
        public long getCancelledClasses() { return cancelledClasses; }
        public long getTotalParticipants() { return totalParticipants; }
    }

    public static class RecordedClassReportResponse {
        private long totalRecordedClasses;
        private long availableRecordings;
        private long expiredRecordings;
        private long deletedRecordings;
        private long totalAccessLogs;
        private long totalProgressRecords;

        public RecordedClassReportResponse(long totalRecordedClasses, long availableRecordings,
                                           long expiredRecordings, long deletedRecordings,
                                           long totalAccessLogs, long totalProgressRecords) {
            this.totalRecordedClasses = totalRecordedClasses;
            this.availableRecordings = availableRecordings;
            this.expiredRecordings = expiredRecordings;
            this.deletedRecordings = deletedRecordings;
            this.totalAccessLogs = totalAccessLogs;
            this.totalProgressRecords = totalProgressRecords;
        }

        public long getTotalRecordedClasses() { return totalRecordedClasses; }
        public long getAvailableRecordings() { return availableRecordings; }
        public long getExpiredRecordings() { return expiredRecordings; }
        public long getDeletedRecordings() { return deletedRecordings; }
        public long getTotalAccessLogs() { return totalAccessLogs; }
        public long getTotalProgressRecords() { return totalProgressRecords; }
    }
}
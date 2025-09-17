//package com.studentmanagementsystem.api.serviceimpl;
//
//import java.io.ByteArrayInputStream;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.time.LocalDate;
//
//import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
//@Service
//public class ExcelGenerator {
//
//    public ByteArrayInputStream quarterlyAttendanceToExcel(List<MonthlyAbsenceDto> reports) {
//
//        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//
//            Sheet sheet = workbook.createSheet("attendance_report");
//
//            // Header
//            String[] headers = {"Student ID", "First Name", "Middle Name", "Last Name",
//                    "Total Working Days", "Absent Count", "Absent Dates..."};
//
//            Row headerRow = sheet.createRow(0);
//            for (int col = 0; col < headers.length; col++) {
//                Cell cell = headerRow.createCell(col);
//                cell.setCellValue(headers[col]);
//            }
//
//            // Data
//            int rowIdx = 1;
//            for (MonthlyAbsenceDto dto : reports) {
//                Row row = sheet.createRow(rowIdx++);
//
//                row.createCell(0).setCellValue(dto.getStudentId());
//                row.createCell(1).setCellValue(dto.getFirstName());
//                row.createCell(2).setCellValue(dto.getMiddleName());
//                row.createCell(3).setCellValue(dto.getLastName());
//                row.createCell(4).setCellValue(dto.getTotalWorkingDays());
//                row.createCell(5).setCellValue(dto.getAbsentCount());
//
//                // Attendance Dates (List<LocalDate>) -> each in its own column
//                int colIdx = 6;
//                if (dto.getAttendanceDate() != null) {
//                    for (LocalDate d : dto.getAttendanceDate()) {
//                        row.createCell(colIdx++).setCellValue(d.toString());
//                    }
//                }
//            }
//
//            workbook.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage(), e);
//        }
//    }
//}

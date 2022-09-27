package com.ace.ailpv.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.ace.ailpv.entity.StudentReport;
import com.ace.ailpv.entity.User;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class ReportService {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentReportService studentReportService;

    @Autowired
    private UserScheduleService userScheduleService;

    public void exportReport(String reportFormat, HttpServletResponse response, Long batchId) throws JRException, IOException {
        createReportTable(batchId);
        String path = "C:\\Users\\Ahkar Toe Maw\\Documents\\Jasper Report\\Student Report";
        List<StudentReport> students = studentReportService.getAllData(); 
        // load file and compile it
        File file = ResourceUtils.getFile("classpath:student.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSourse = new JRBeanCollectionDataSource(students);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourse);
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\student.pdf");
        }
        if (reportFormat.equalsIgnoreCase("excel")) {
            JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); // set compiled report as input
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + "\\student.xlsx")); // set output
                                                                                                        // file via path
                                                                                                        // with filename
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true); // setup configuration
            configuration.setDetectCellType(true);
            exporter.setConfiguration(configuration); // set configuration
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setContentType("application/xlsx");
            response.addHeader("Content-Disposition", "inline; filename=student.xlsx;");
            exporter.exportReport();
        }
    }

    public void createReportTable(Long batchId) {
        List<User> studentList = userService.getStudentListByBatchId(batchId);
        List<StudentReport> studentReportList = studentReportService.getAllData();

        if (studentReportService.getAllData() != null) {
            for (StudentReport student : studentReportList) {
                studentReportService.deleteStudentById(student.getId());
            }
        }

        for (User student : studentList) {
            StudentReport reqStudent = new StudentReport();
            reqStudent.setStudent_id(student.getId());
            reqStudent.setName(student.getName());
            reqStudent.setBatch(student.getBatchList().get(0).getName());
            Float attendancePercentage = userScheduleService.avgAttendaceOfStudent(student.getId()).floatValue();
            reqStudent.setAttendance_percentage(Math.round(attendancePercentage) + "%");
            studentReportService.addStudentReport(reqStudent);
        }

    }

}

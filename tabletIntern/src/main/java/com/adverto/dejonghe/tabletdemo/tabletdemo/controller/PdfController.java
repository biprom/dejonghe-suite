package com.adverto.dejonghe.tabletdemo.tabletdemo.controller;

import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.services.WorkOrderPdfServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class PdfController {

    @Value( "${rootFolder}" )
    private String rootFolder;

    @Autowired
    WorkOrderPdfServices workOrderPdfServices;
    @Autowired
    WorkOrderService workOrderService;

    private String pdfNaam;


    @GetMapping("/pdf/workorder/{workOrderId}")
    public ResponseEntity<Resource> getWorkOrderPdf(
            @PathVariable String workOrderId
    ) {
        Optional<WorkOrder> optionalWorkOrder =
                workOrderService.getWorkOrderById(workOrderId);

        if (optionalWorkOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            String pdfNaam = workOrderPdfServices
                    .generateWorkOrderPDF(optionalWorkOrder.get());

            Path rootPath = Paths.get(rootFolder)
                    .toAbsolutePath()
                    .normalize();

            Path pdfPath = rootPath.resolve(pdfNaam).normalize();

            if (!pdfPath.startsWith(rootPath) || !Files.exists(pdfPath)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new InputStreamResource(
                    Files.newInputStream(pdfPath)
            );

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + pdfPath.getFileName() + "\""
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(Files.size(pdfPath))
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    public void setPdfNaam(String pdfNaam) {
        this.pdfNaam = pdfNaam;
    }

}

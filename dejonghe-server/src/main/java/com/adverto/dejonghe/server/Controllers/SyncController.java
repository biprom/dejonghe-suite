package com.adverto.dejonghe.server.Controllers;

import com.adverto.dejonghe.common.dbservices.*;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.common.entities.product.product.*;
import com.adverto.dejonghe.common.entities.restEntities.MediaUploadResponse;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class SyncController {

    private final GridFSBucket gridFSBucket;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final WorkOrderService workOrderService;

    private final ProductLevel1Service productLevel1Service;
    private final ProductLevel2Service productLevel2Service;
    private final ProductLevel3Service productLevel3Service;
    private final ProductLevel4Service productLevel4Service;
    private final ProductLevel5Service productLevel5Service;
    private final ProductLevel6Service productLevel6Service;
    private final ProductLevel7Service productLevel7Service;

    @Value("${app.update.folder.local}")
    private String updateFolderLocal;

    @Value("${app.update.folder.remote}")
    private String updateFolderRemote;

    @GetMapping("/employees")
    public List<Employee> getEmployeesChangedSince(
            //@RequestParam LocalDateTime since
    ) {
        return employeeService.getAll().get();
    }

    @GetMapping("/customers")
    public List<Customer> getCustomersChangedSince(
            //@RequestParam LocalDateTime since
    ) {
        return customerService.getAllCustomers().get();
    }

    @GetMapping("/products")
    public List<Product> getProductsChangedSince(
            //@RequestParam LocalDateTime since
    ) {
        return productService.getAllProducts().get();
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<byte[]> downloadDocument(
            @PathVariable String id
    ) throws Exception {

        ObjectId objectId = new ObjectId(id);

        GridFSFile file =
                gridFSBucket.find(
                        Filters.eq("_id", objectId)
                ).first();

        if(file == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        gridFSBucket.downloadToStream(
                objectId,
                outputStream
        );

        String contentType = "application/octet-stream";

        if(file.getMetadata() != null &&
                file.getMetadata().getString("contentType") != null) {

            contentType =
                    file.getMetadata().getString("contentType");
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                file.getFilename() + "\""
                )
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .body(outputStream.toByteArray());
    }


    @PostMapping("/workOrderToServer")
    public ResponseEntity<WorkOrder[]> saveWorkOrder(
            @RequestBody List<WorkOrder> workOrderListToSave
    ) {
        if((workOrderListToSave != null) && (workOrderListToSave.size() > 0)) {
            for(WorkOrder workOrder : workOrderListToSave) {
                workOrderService.save(workOrder);
            }
        }
        return ResponseEntity.ok(workOrderListToSave.toArray(WorkOrder[]::new));
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<MediaUploadResponse> upload(
            @RequestParam("id") String id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        ObjectId objectId = new ObjectId(id);

        GridFSUploadOptions options =
                new GridFSUploadOptions()
                        .metadata(
                                new Document("contentType",
                                        file.getContentType())
                        );

        try{
            gridFSBucket.uploadFromStream(
                    new BsonObjectId(objectId),
                    file.getOriginalFilename(),
                    file.getInputStream()
            );
        }
        catch (Exception e){
            return ResponseEntity.ok(
                    new MediaUploadResponse(id)
            );
        }

        return ResponseEntity.ok(
                new MediaUploadResponse(id)
        );
    }

    @GetMapping("/workorderToTablet")
    public ResponseEntity<List<WorkOrder>> getWorkOrders() {

        Optional<List<WorkOrder>> allWorkOrdersByStatusRunning = workOrderService.getAllWorkOrdersByStatus(WorkOrderStatus.RUNNING);
        if((allWorkOrdersByStatusRunning.isPresent()) && (allWorkOrdersByStatusRunning.get().size() > 0)) {
            List<WorkOrder> workOrders =
                    allWorkOrdersByStatusRunning.get();
            return ResponseEntity.ok(workOrders);
        }
        else {
            return ResponseEntity.ok(
                    allWorkOrdersByStatusRunning
                            .orElse(Collections.emptyList())
            );
        }

    }

    @GetMapping("/update-tablet-local")
    public ResponseEntity<Resource> downloadTabletLocalJar()
            throws IOException {

        Path path = Paths.get(
                updateFolderLocal,
                "tabletIntern-1.0-SNAPSHOT.jar"
        );

        Resource resource =
                new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=tabletIntern-1.0-SNAPSHOT.jar"
                )
                .body(resource);
    }

    @GetMapping("/update-tablet-remote")
    public ResponseEntity<Resource> downloadTabletRemoteJar()
            throws IOException {

        Path path = Paths.get(
                updateFolderRemote,
                "tabletExtern-1.0-SNAPSHOT.jar"
        );

        Resource resource =
                new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=tabletExtern-1.0-SNAPSHOT.jar"
                )
                .body(resource);
    }

    @GetMapping("/productLevel1")
    public List<ProductLevel1> getProductLevel1(
            //@RequestParam LocalDateTime since
    ) {
        Optional<List<ProductLevel1>> allOptProductLevel1 = productLevel1Service.getAllProductLevel1();
        if((allOptProductLevel1.isPresent()) && (allOptProductLevel1.get().size() > 0)) {
            return allOptProductLevel1.get();
        }
        else{
            return Collections.emptyList();
        }
    }

    @GetMapping("/productLevel2")
    public List<ProductLevel2> getProductLevel2(
            //@RequestParam LocalDateTime since
    ) {
        Optional<List<ProductLevel2>> allOptProductLevel2 = productLevel2Service.getAllProductLevel2();
        if((allOptProductLevel2.isPresent()) && (allOptProductLevel2.get().size() > 0)) {
            return allOptProductLevel2.get();
        }
        else{
            return Collections.emptyList();
        }
    }

    @GetMapping("/productLevel3")
    public List<ProductLevel3> getProductLevel3(
            //@RequestParam LocalDateTime since
    ) {
        Optional<List<ProductLevel3>> allOptProductLevel3 = productLevel3Service.getAllProductLevel3();
        if((allOptProductLevel3.isPresent()) && (allOptProductLevel3.get().size() > 0)) {
            return allOptProductLevel3.get();
        }
        else{
            return Collections.emptyList();
        }
    }

    @GetMapping("/productLevel4")
    public List<ProductLevel4> getProductLevel4(
            //@RequestParam LocalDateTime since
    ) {
        Optional<List<ProductLevel4>> allOptProductLevel4 = productLevel4Service.getAllProductLevel4();
        if((allOptProductLevel4.isPresent()) && (allOptProductLevel4.get().size() > 0)) {
            return allOptProductLevel4.get();
        }
        else{
            return Collections.emptyList();
        }
    }

    @GetMapping("/productLevel5")
    public List<ProductLevel5> getProductLevel5(
            //@RequestParam LocalDateTime since
    ) {
        Optional<List<ProductLevel5>> allOptProductLevel5 = productLevel5Service.getAllProductLevel5();
        if((allOptProductLevel5.isPresent()) && (allOptProductLevel5.get().size() > 0)) {
            return allOptProductLevel5.get();
        }
        else{
            return Collections.emptyList();
        }
    }

    @GetMapping("/productLevel6")
    public List<ProductLevel6> getProductLevel6(
            //@RequestParam LocalDateTime since
    ) {
        Optional<List<ProductLevel6>> allOptProductLevel6 = productLevel6Service.getAllProductLevel6();
        if((allOptProductLevel6.isPresent()) && (allOptProductLevel6.get().size() > 0)) {
            return allOptProductLevel6.get();
        }
        else{
            return Collections.emptyList();
        }
    }

    @GetMapping("/productLevel7")
    public List<ProductLevel7> getProductLevel7(
            //@RequestParam LocalDateTime since
    ) {
        Optional<List<ProductLevel7>> allOptProductLevel7 = productLevel7Service.getAllProductLevel7();
        if((allOptProductLevel7.isPresent()) && (allOptProductLevel7.get().size() > 0)) {
            return allOptProductLevel7.get();
        }
        else{
            return Collections.emptyList();
        }
    }
}

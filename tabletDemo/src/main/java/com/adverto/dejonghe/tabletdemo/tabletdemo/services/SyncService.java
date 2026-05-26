package com.adverto.dejonghe.tabletdemo.tabletdemo.services;

import com.adverto.dejonghe.common.dbservices.*;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.product.product.*;
import com.adverto.dejonghe.common.entities.restEntities.MediaUploadResponse;
import com.adverto.dejonghe.common.repos.CustomerRepo;
import com.adverto.dejonghe.common.repos.EmployeeRepo;
import com.adverto.dejonghe.common.repos.ProductRepo;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import lombok.RequiredArgsConstructor;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SyncService {

    private final ProductLevel1Service productLevel1Service;
    private final ProductLevel2Service productLevel2Service;
    private final ProductLevel3Service productLevel3Service;
    private final ProductLevel4Service productLevel4Service;
    private final ProductLevel5Service productLevel5Service;
    private final ProductLevel6Service productLevel6Service;
    private final ProductLevel7Service productLevel7Service;

    private final EmployeeService employeeService;
    private final EmployeeRepo employeeRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;
    private final WorkOrderService workOrderService;
    private final RestTemplate restTemplate;
    private final GridFSBucket localGridFSBucket;

    public void syncEmployeesFromServer() {

        String url =
                "http://192.168.1.90:8080/api/employees";

        ResponseEntity<Employee[]> response =
                restTemplate.getForEntity(
                        url,
                        Employee[].class
                );

        List<Employee> employees =
                Arrays.asList(response.getBody());

        employeeRepo.deleteAll();
        employeeRepo.saveAll(employees);
    }

    public void syncCustomersFromServer() {

        String url =
                "http://192.168.1.90:8080/api/customers";

        ResponseEntity<Customer[]> response =
                restTemplate.getForEntity(
                        url,
                        Customer[].class
                );

        List<Customer> customers =
                Arrays.asList(response.getBody());

        customerRepo.deleteAll();
        customerRepo.saveAll(customers);
    }

    public void syncProducts() {

        productRepo.deleteAll();

        String url =
                "http://192.168.1.90:8080/api/products";

        ResponseEntity<Product[]> response =
                restTemplate.getForEntity(
                        url,
                        Product[].class
                );

        List<Product> products =
                Arrays.asList(response.getBody());

        for(Product product : products) {

            productRepo.save(product);

            // sync documents
            if(product.getImageList() != null && product.getImageList().size() > 0) {
                receiveDocuments(product.getImageList());
            }

            if(product.getPdfList() != null && product.getPdfList().size() > 0){
                receiveDocuments(product.getPdfList());
            }
        }
    }

    private void receiveDocuments(List<String> documentIds) {

        if((documentIds != null) && (!documentIds.isEmpty())){
            for(String documentId : documentIds) {
                downloadAndStoreDocument(documentId);
            }
        }
    }


    private void downloadAndStoreDocument(
            String documentId
    ) {

        try {

            String url =
                    "http://192.168.1.90:8080" +
                            "/api/documents/" +
                            documentId;

            ResponseEntity<byte[]> response =
                    restTemplate.getForEntity(
                            url,
                            byte[].class
                    );

            byte[] bytes = response.getBody();

            if(bytes == null) {
                return;
            }

            storeDocumentLocally(
                    documentId,
                    bytes
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void storeDocumentLocally(
            String documentId,
            byte[] bytes
    ) {

        try {

            ObjectId objectId =
                    new ObjectId(documentId);

            GridFSUploadOptions options =
                    new GridFSUploadOptions()
                            .metadata(
                                    new Document(
                                            "contentType",
                                            "application/octet-stream"
                                    )
                            );

            localGridFSBucket.uploadFromStream(
                    new BsonObjectId(objectId),
                    documentId,
                    new ByteArrayInputStream(bytes)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //send workOrders to server
    public void sendWorkOrders() throws IOException {
        Optional<List<WorkOrder>> allWorkOrders = workOrderService.getAll();
        if(allWorkOrders.isPresent() && allWorkOrders.get().size() > 0) {
            String url =
                    "http://192.168.1.90:8080/api/workOrderToServer";

            ResponseEntity<WorkOrder[]> response =
                    restTemplate.postForEntity(
                            url,
                            allWorkOrders.get(),
                            WorkOrder[].class
                    );

            List<WorkOrder> saved =
                    Arrays.asList(response.getBody());

            //send all images from the WorkOrders to the server
            for(WorkOrder workOrder : saved){
                if(workOrder.getImageList() != null && workOrder.getImageList().size() > 0){
                    for(String id : workOrder.getImageList()){
                        uploadDocumentToServer(id,getDocumentBytes(id));
                    }
                }
                workOrderService.delete(workOrder);
            }
        }
    }


    public void uploadDocumentToServer(
            String id,
            byte[] bytes
    ) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("id", id);

        body.add(
                "file",
                new ByteArrayResource(bytes) {

                    @Override
                    public String getFilename() {
                        return "photo.jpg";
                    }
                }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.MULTIPART_FORM_DATA
        );

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(
                "http://192.168.1.90:8080/api/upload",
                request,
                MediaUploadResponse.class
        );
    }

    public byte[] getDocumentBytes(String id)
            throws IOException {

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        localGridFSBucket.downloadToStream(
                new ObjectId(id),
                outputStream
        );

        return outputStream.toByteArray();
    }


    //receive workorders from server

    public void receiveWorkOrders() throws IOException {
        String url =
                "http://192.168.1.90:8080/api/workorderToTablet";

        ResponseEntity<WorkOrder[]> response =
                restTemplate.getForEntity(
                        url,
                        WorkOrder[].class
                );

        List<WorkOrder> workOrders =
                Arrays.asList(response.getBody());

        for(WorkOrder workOrder : workOrders){
            if(workOrder.getImageList() != null && workOrder.getImageList().size() > 0){
                for(String id : workOrder.getImageList()){
                    downloadAndStoreDocument(id);
                }
            }
            workOrderService.save(workOrder);
        }
    }

    public void deleteAllDocuments() {

        GridFSFindIterable files =
                localGridFSBucket.find();

        for(GridFSFile file : files) {

            localGridFSBucket.delete(
                    file.getObjectId()
            );
        }
    }

    public void syncProductFolders1() {

        productLevel1Service.removeAll();

        String url =
                "http://192.168.1.90:8080/api/productLevel1";

        ResponseEntity<ProductLevel1[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductLevel1[].class
                );

        List<ProductLevel1> productLevel1s =
                Arrays.asList(response.getBody());

        for(ProductLevel1 productLevel1 : productLevel1s) {
            productLevel1Service.saveProductLevel1(productLevel1);
        }
    }

    public void syncProductFolders2() {

        productLevel2Service.removeAll();

        String url =
                "http://192.168.1.90:8080/api/productLevel2";

        ResponseEntity<ProductLevel2[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductLevel2[].class
                );

        List<ProductLevel2> productLevel2s =
                Arrays.asList(response.getBody());

        for(ProductLevel2 productLevel2 : productLevel2s) {
            productLevel2Service.saveProductLevel2(productLevel2);
        }
    }

    public void syncProductFolders3() {

        productLevel3Service.removeAll();

        String url =
                "http://192.168.1.90:8080/api/productLevel3";

        ResponseEntity<ProductLevel3[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductLevel3[].class
                );

        List<ProductLevel3> productLevel3s =
                Arrays.asList(response.getBody());

        for(ProductLevel3 productLevel3 : productLevel3s) {
            productLevel3Service.saveProductLevel3(productLevel3);
        }
    }

    public void syncProductFolders4() {

        productLevel4Service.removeAll();

        String url =
                "http://192.168.1.90:8080/api/productLevel4";

        ResponseEntity<ProductLevel4[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductLevel4[].class
                );

        List<ProductLevel4> productLevel4s =
                Arrays.asList(response.getBody());

        for(ProductLevel4 productLevel4 : productLevel4s) {
            productLevel4Service.saveProductLevel4(productLevel4);
        }
    }

    //mod
    public void syncProductFolders5() {

        productLevel5Service.removeAll();

        String url =
                "http://192.168.1.90:8080/api/productLevel5";

        ResponseEntity<ProductLevel5[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductLevel5[].class
                );

        List<ProductLevel5> productLevel5s =
                Arrays.asList(response.getBody());

        for(ProductLevel5 productLevel5 : productLevel5s) {
            productLevel5Service.saveProductLevel5(productLevel5);
        }
    }

    public void syncProductFolders6() {

        productLevel6Service.removeAll();

        String url =
                "http://192.168.1.90:8080/api/productLevel6";

        ResponseEntity<ProductLevel6[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductLevel6[].class
                );

        List<ProductLevel6> productLevel6s =
                Arrays.asList(response.getBody());

        for(ProductLevel6 productLevel6 : productLevel6s) {
            productLevel6Service.saveProductLevel6(productLevel6);
        }
    }

    public void syncProductFolders7() {

        productLevel7Service.removeAll();

        String url =
                "http://192.168.1.90:8080/api/productLevel7";

        ResponseEntity<ProductLevel7[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductLevel7[].class
                );

        List<ProductLevel7> productLevel7s =
                Arrays.asList(response.getBody());

        for(ProductLevel7 productLevel7 : productLevel7s) {
            productLevel7Service.saveProductLevel7(productLevel7);
        }
    }
}


package com.adverto.dejonghe.common.entities.product.product;

import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.enums.product.ORDER_PRODUCT_STATUS;
import com.adverto.dejonghe.common.entities.enums.product.VAT;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    private String id;

    private LocalDate date;
    private LocalDate orderDate;
    private ORDER_PRODUCT_STATUS status;
    private Double selectedAmount;
    private Employee employee;
    private String orderCode = "";
    private String internalName = "";
    private String abbreviation;
    private String positionNumber;
    private String unit;
    private String comment;
    private ProductLevel1 productLevel1;
    private ProductLevel2 productLevel2;
    private ProductLevel3 productLevel3;
    private ProductLevel4 productLevel4;
    private ProductLevel5 productLevel5;
    private ProductLevel6 productLevel6;
    private ProductLevel7 productLevel7;
}

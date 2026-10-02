package com.adverto.dejonghe.common.entities.product.product;

import com.adverto.dejonghe.common.entities.enums.product.VAT;
import com.adverto.dejonghe.common.wizard.WIZARD;
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
public class Product implements Serializable {
    @Id
    private String id;

    private LocalDate date;
    private String dateToShowOnInvoice;
    private Boolean showDate = Boolean.TRUE;
    private Boolean option;
    private Boolean set;
    private Boolean setElement;
    private Double selectedAmount;
    private String productCode = "";
    private String internalName = "";
    private String abbreviation;
    private Double purchasePrice = 0.0;
    private Double sellPrice = 0.0;
    private Double sellPriceIndustry = 0.0;
    private Double sellMargin = 0.0;
    private Double sellMarginIndustry = 0.0;
    private Double totalPrice = 0.0;
    private VAT vat = VAT.EENENTWINTIG;
    private String positionNumber;
    private String unit;
    private String moq;
    private String comment;
    private List<PurchasePrice> purchasePriseList;
    private List<String>buddyList;
    private Boolean linked;
    private ProductLevel1 productLevel1;
    private ProductLevel2 productLevel2;
    private ProductLevel3 productLevel3;
    private ProductLevel4 productLevel4;
    private ProductLevel5 productLevel5;
    private ProductLevel6 productLevel6;
    private ProductLevel7 productLevel7;
    private Integer teamNumber;
    private Boolean bWorkHour = Boolean.FALSE;
    private Boolean bComment = Boolean.FALSE;
    private Boolean bTravel = Boolean.FALSE;

    //for making an attachement
    private Boolean bSelectedForAttachement = false;
    private Boolean bAttachement = false;
    private LocalDate attachementNumber;

    //for making a merged product (total product for a couple products)
    private Boolean mergedProduct = false;
    //for making a merged invisible product on a PDF (total product for a couple products)
    private Boolean mergedInvisibleProduct = Boolean.FALSE;
    List<Product>mergedProducts;

    private Boolean remark = false;
    private List<Product>setList;
    List<String>imageList;
    List<String>pdfList;
    List<ProductLink>linkDocumentList;
    boolean boldMode = false;
    private WIZARD wizard;
    @Transient
    boolean selectedMode = false;
    boolean noRecentPriceApproved = false;
    @Transient
    boolean noRecentPrice = false;
    @Transient
    double recentPurchasePrice;
    @Transient
    double recentIndustryPrice;
    @Transient
    double recentAgroPrice;

}

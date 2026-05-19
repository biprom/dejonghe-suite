package com.adverto.dejonghe.common.services;

import com.adverto.dejonghe.common.dbservices.*;
import com.adverto.dejonghe.common.entities.product.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServices {

    @Autowired
    ProductService productService;

    @Autowired
    ProductLevel1Service productLevel1Service;

    @Autowired
    ProductLevel2Service productLevel2Service;

    @Autowired
    ProductLevel3Service productLevel3Service;

    @Autowired
    ProductLevel4Service productLevel4Service;

    @Autowired
    ProductLevel5Service productLevel5Service;

    @Autowired
    ProductLevel6Service productLevel6Service;

    @Autowired
    ProductLevel7Service productLevel7Service;

    List<Product>coupledProductList = new ArrayList<>();

    public ProductServices(ProductService productService) {
        this.productService = productService;
    }
    
    public List<String>getDeviceTypesBasedOnFolders(){
        ProductLevel1 productLevel1 = productLevel1Service.getAllProductLevel1().get().stream().filter(x -> x.getName().matches("Toestellen")).findFirst().get();
        Optional<List<ProductLevel2>> level2DeviceTypes = productLevel2Service.getProductLevel2sFromPreviousLevels(productLevel1);
        Optional<List<ProductLevel3>> level3DeviceTypes = productLevel3Service.getProductLevel3sFromPreviousLevels(level2DeviceTypes.get().stream().filter(x -> x.getName().matches("Separatietechnieken")).findFirst().get(), productLevel1);
        List<String> result = Stream.concat(
                level2DeviceTypes.get().stream().map(x -> x.getName()),
                level3DeviceTypes.get().stream().map(x -> x.getName())
        ).collect(Collectors.toCollection(ArrayList::new));
        result.removeIf(item -> "Separatietechnieken".equals(item));
        return result;
    }

    public Optional<List<Product>> getCoupledProducts(Product product){

        coupledProductList.clear();

        if((product.getBuddyList() != null) && (product.getBuddyList().size() > 0)){
            Optional<List<Product>> productsById = productService.getProductsById(product.getBuddyList());
            if(productsById.isPresent()){
                coupledProductList.addAll(productsById.get());
            }
        }

        if((product.getProductCode() != null) && (product.getProductCode().startsWith("RVS-B-"))){
            String size = String.valueOf(extractNumberBeforeX(product.getProductCode()));

            //find nut for this bolt
            Optional<List<Product>> optNuts = productService.findByProductCodeEqualCaseInsensitive("RVS-M-M" + size);
            if((!optNuts.isEmpty()) && (optNuts.isPresent())){
                //optNuts.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount()));
                coupledProductList.addAll(optNuts.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find nut for this bolt
            Optional<List<Product>> optGuarantNuts = productService.findByProductCodeEqualCaseInsensitive("RVS-BM-M" + size);
            if((!optGuarantNuts.isEmpty()) && (optGuarantNuts.isPresent())){
                //optGuarantNuts.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount()));
                coupledProductList.addAll(optGuarantNuts.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find small round for this bolt
            Optional<List<Product>> optSmallRound = productService.findByProductCodeEqualCaseInsensitive("RVS-VK-M" +size);
            if((!optSmallRound.isEmpty()) && (optSmallRound.isPresent())){
                //optSmallRound.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount() * 2));
                coupledProductList.addAll(optSmallRound.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find large round for this bolt
            Optional<List<Product>> optLargeRound = productService.findByProductCodeEqualCaseInsensitive("RVS-VG-M" +size);
            if((!optLargeRound.isEmpty()) && (optLargeRound.isPresent())){
                //optLargeRound.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount() * 2));
                coupledProductList.addAll(optLargeRound.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find spring round for this bolt
            Optional<List<Product>> optSpringRound = productService.findByProductCodeEqualCaseInsensitive("RVS-SV-M" +size);
            if((!optSpringRound.isEmpty()) && (optSpringRound.isPresent())){
                //optSpringRound.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount() * 2));
                coupledProductList.addAll(optSpringRound.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }
        }

        if((product.getProductCode() != null) && (product.getProductCode().startsWith("RVS-IB-"))){
            String size = String.valueOf(extractNumberBeforeX(product.getProductCode()));

            //find nut for this bolt
            Optional<List<Product>> optNuts = productService.findByProductCodeEqualCaseInsensitive("RVS-M-M" + size);
            if((!optNuts.isEmpty()) && (optNuts.isPresent())){
                //optNuts.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount()));
                coupledProductList.addAll(optNuts.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find nut for this bolt
            Optional<List<Product>> optGuarantNuts = productService.findByProductCodeEqualCaseInsensitive("RVS-BM-M" + size);
            if((!optGuarantNuts.isEmpty()) && (optGuarantNuts.isPresent())){
                //optGuarantNuts.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount()));
                coupledProductList.addAll(optGuarantNuts.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find small round for this bolt
            Optional<List<Product>> optSmallRound = productService.findByProductCodeEqualCaseInsensitive("RVS-VK-M" +size);
            if((!optSmallRound.isEmpty()) && (optSmallRound.isPresent())){
                //optSmallRound.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount() * 2));
                coupledProductList.addAll(optSmallRound.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find large round for this bolt
            Optional<List<Product>> optLargeRound = productService.findByProductCodeEqualCaseInsensitive("RVS-VG-M" +size);
            if((!optLargeRound.isEmpty()) && (optLargeRound.isPresent())){
                //optLargeRound.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount() * 2));
                coupledProductList.addAll(optLargeRound.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }

            //find spring round for this bolt
            Optional<List<Product>> optSpringRound = productService.findByProductCodeEqualCaseInsensitive("RVS-SV-M" +size);
            if((!optSpringRound.isEmpty()) && (optSpringRound.isPresent())){
                //optSpringRound.get().stream().forEach(item -> item.setSelectedAmount(product.getSelectedAmount() * 2));
                coupledProductList.addAll(optSpringRound.get().stream().filter(item -> item.getProductLevel1().getName().contains("Montagemateriaal")).collect(Collectors.toList()));
            }
        }

        return Optional.of(coupledProductList);
    }

    public static int extractNumberBeforeX(String s) {
        // Regex zoekt naar 1 of 2 cijfers voor een 'x'
        Pattern pattern = Pattern.compile("(\\d{1,2})(?=x)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            // Teruggeven als integer
            return Integer.parseInt(matcher.group(1));
        }

        // Geen match gevonden
        throw new IllegalArgumentException("No valid number found before 'x'");
    }

    public void calcSellPriceAgroFromPurchasePriceAndMargin(Product product) {
        try{
            product.setSellPrice(product.getPurchasePrice() *(product.getSellMargin()));
            }
        catch (Exception e){

        }
    }

    public void calcSellPriceIndustryFromPurchasePriceAndMarginIndustry(Product product) {
        try{
            product.setSellPriceIndustry(product.getPurchasePrice() *(product.getSellMarginIndustry()));
        }
        catch (Exception e){

        }
    }

    public Boolean checkIfProductIsInEndFolder(Product product) {

        if(product.getProductLevel6() != null){
            if(productLevel7Service.getProductLevel7ByPreviousLevelNames(product.getProductLevel6(),product.getProductLevel5(),product.getProductLevel4(),product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return true;
            }
        }

        if(product.getProductLevel5() != null){
            if(productLevel6Service.getProductLevel6ByPreviousLevelNames(product.getProductLevel5(),product.getProductLevel4(),product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return true;
            }
        }

        if(product.getProductLevel4() != null){
            if(productLevel5Service.getProductLevel5ByPreviousLevelNames(product.getProductLevel4(),product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return true;
            }
        }

        if(product.getProductLevel3() != null){
            if(productLevel4Service.getProductLevel4ByPreviousLevelNames(product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return true;
            }
        }

        if(product.getProductLevel2() != null){
            if(productLevel3Service.getProductLevel3sFromPreviousLevels(product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return true;
            }
        }

        if(product.getProductLevel1() != null){
            if(productLevel2Service.getProductLevel2sFromPreviousLevels(product.getProductLevel1()).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public String getEndFolder(Product product) {
        if(product.getProductLevel6() != null){
            if(productLevel7Service.getProductLevel7ByPreviousLevelNames(product.getProductLevel6(),product.getProductLevel5(),product.getProductLevel4(),product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return product.getProductLevel6().getName();
            }
        }

        if(product.getProductLevel5() != null){
            if(productLevel6Service.getProductLevel6ByPreviousLevelNames(product.getProductLevel5(),product.getProductLevel4(),product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return product.getProductLevel5().getName();
            }
        }

        if(product.getProductLevel4() != null){
            if(productLevel5Service.getProductLevel5ByPreviousLevelNames(product.getProductLevel4(),product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return product.getProductLevel4().getName();
            }
        }

        if(product.getProductLevel3() != null){
            if(productLevel4Service.getProductLevel4ByPreviousLevelNames(product.getProductLevel3(),product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return product.getProductLevel3().getName();
            }
        }

        if(product.getProductLevel2() != null){
            if(productLevel3Service.getProductLevel3sFromPreviousLevels(product.getProductLevel2(),product.getProductLevel1()).isEmpty()){
                return product.getProductLevel2().getName();
            }
        }

        if(product.getProductLevel1() != null){
            if(productLevel2Service.getProductLevel2sFromPreviousLevels(product.getProductLevel1()).isEmpty()){
                return product.getProductLevel1().getName();
            }
        }

        return "";

    }

    public boolean haveSameLevels(Product p1, Product p2) {
        return sameName(p1.getProductLevel1(), p2.getProductLevel1()) &&
                sameName(p1.getProductLevel2(), p2.getProductLevel2()) &&
                sameName(p1.getProductLevel3(), p2.getProductLevel3()) &&
                sameName(p1.getProductLevel4(), p2.getProductLevel4()) &&
                sameName(p1.getProductLevel5(), p2.getProductLevel5()) &&
                sameName(p1.getProductLevel6(), p2.getProductLevel6()) &&
                sameName(p1.getProductLevel7(), p2.getProductLevel7());
    }

    private boolean sameName(ProductLevel1 l1, ProductLevel1 l2) {
        return Objects.equals(
                l1 != null ? l1.getName() : null,
                l2 != null ? l2.getName() : null
        );
    }

    private boolean sameName(ProductLevel2 l1, ProductLevel2 l2) {
        return Objects.equals(
                l1 != null ? l1.getName() : null,
                l2 != null ? l2.getName() : null
        );
    }

    private boolean sameName(ProductLevel3 l1, ProductLevel3 l2) {
        return Objects.equals(
                l1 != null ? l1.getName() : null,
                l2 != null ? l2.getName() : null
        );
    }

    private boolean sameName(ProductLevel4 l1, ProductLevel4 l2) {
        return Objects.equals(
                l1 != null ? l1.getName() : null,
                l2 != null ? l2.getName() : null
        );
    }

    private boolean sameName(ProductLevel5 l1, ProductLevel5 l2) {
        return Objects.equals(
                l1 != null ? l1.getName() : null,
                l2 != null ? l2.getName() : null
        );
    }

    private boolean sameName(ProductLevel6 l1, ProductLevel6 l2) {
        return Objects.equals(
                l1 != null ? l1.getName() : null,
                l2 != null ? l2.getName() : null
        );
    }

    private boolean sameName(ProductLevel7 l1, ProductLevel7 l2) {
        return Objects.equals(
                l1 != null ? l1.getName() : null,
                l2 != null ? l2.getName() : null
        );
    }
}

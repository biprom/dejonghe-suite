package com.adverto.dejonghe.common.implementations;

import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.product.product.Product;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ProductImplementation implements JRDataSource {


    private int lastFiledAdded;
    private HashMap<String, Integer>fieldsNumber = new HashMap<>(  );
    List<Product>products;
    Customer selectedCustomer;
    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));

    public ProductImplementation(List<Product>products, Customer selectedCustomer) {
        setUpNumberFormat();
        this.products = products;
        this.selectedCustomer = selectedCustomer;
        lastFiledAdded = products.size() ;
    }
    public ProductImplementation(List<Product>products) {
        setUpNumberFormat();
        this.products = products;
        this.selectedCustomer = null;
        lastFiledAdded = products.size() ;
    }

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    @Override
    public boolean next() throws JRException {
        if(lastFiledAdded > 0 ){
            lastFiledAdded --;
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if (jrField.getName().equals("Datum")) {
            try{
                if((products.get(lastFiledAdded).getDateToShowOnInvoice() != null)){
                    return products.get(lastFiledAdded).getDateToShowOnInvoice();
                }
                else{
                    return null;
                }
            }
            catch (Exception e){
                return null;
            }

        } else if (jrField.getName().equals("Omschrijving")) {
            if((products.get(lastFiledAdded).getMergedProduct() != null) && ((products.get(lastFiledAdded).getMergedProduct() == true))){
                return "  " + products.get(lastFiledAdded).getInternalName();
            }
            return products.get(lastFiledAdded).getInternalName();
        } else if (jrField.getName().equals("Aantal")) {
            try{
                String product =  products.get(lastFiledAdded).getSelectedAmount().toString().replace('.',',');
                if (product.endsWith(",0")) {
                    product = product.substring(0, product.length() - 2);
                }
                if(products.get(lastFiledAdded).getBComment()){
                    return null;
                }
                return product;
            }
            catch (Exception e){
                return null;
            }
        } else if ((selectedCustomer != null) && (jrField.getName().equals("Eenheidsprijs"))) {

            try{
                if(selectedCustomer.getBAgro()){
                    if(products.get(lastFiledAdded).getSellPrice() != null){
                        if((products.get(lastFiledAdded).getBComment())){
                            return null;
                        }
                        return df.format(products.get(lastFiledAdded).getSellPrice() ) + " €";
                    }
                    else{
                        return "";
                    }
                }
                else{
                    if(products.get(lastFiledAdded).getSellPriceIndustry() != null){
                        if(products.get(lastFiledAdded).getBComment()){
                            return null;
                        }
                        if((products.get(lastFiledAdded).getSellPriceIndustry().equals(0.0))){
                            if((products.get(lastFiledAdded).getSellPrice().equals(0.0))){
                                return products.get(lastFiledAdded).getSellPriceIndustry() + " €";
                            }
                            return df.format(products.get(lastFiledAdded).getSellPrice() ) + " €";
                        }
                        return df.format(products.get(lastFiledAdded).getSellPriceIndustry() ) + " €";
                    }
                    else{
                        if(products.get(lastFiledAdded).getBComment()){
                            return null;
                        }
                        if((products.get(lastFiledAdded).getSellPrice().equals(0.0))){
                            return null;
                        }
                        return df.format(products.get(lastFiledAdded).getSellPrice() ) + " €";
                    }
                }
            }
            catch (Exception e){
                return "";
            }
        } else if ((selectedCustomer != null) && (jrField.getName().equals("Totaal"))) {
            if(products.get(lastFiledAdded).getTotalPrice() != null){
                if((products.get(lastFiledAdded).getBComment())){
                    return null;
                }
                return products.get(lastFiledAdded).getTotalPrice();
            }
            return null;
        } else if ((selectedCustomer != null) && (jrField.getName().equals("btwStatus"))) {
            if(products.get(lastFiledAdded).getBComment()){
                return null;
            }
            if(products.get(lastFiledAdded).getSelectedAmount() != null){
                if(!products.get(lastFiledAdded).getSelectedAmount().equals(0.0)){
                    if(selectedCustomer.getVatNumber().contains("BE")){
                        return products.get(lastFiledAdded).getVat().getDiscription();
                    }
                    else{
                        return null;
                    }
                }
                else{
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else if (jrField.getName().equals("Commentaar")) {
            if(products.get(lastFiledAdded).getBComment() != null){
                if(products.get(lastFiledAdded).getBComment()){
                    return true;
                }
                else{
                    return false;
                }
            }
            else {
                return null;
            }
        }
        return "";
    }
}

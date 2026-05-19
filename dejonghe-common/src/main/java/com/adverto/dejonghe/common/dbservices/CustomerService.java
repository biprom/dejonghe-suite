package com.adverto.dejonghe.common.dbservices;

import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    List<Address>addressList = new ArrayList<>();

    public String save(Customer sampleCustomer) {
        if (sampleCustomer.getName() != null) {
            Customer save = customerRepo.save(sampleCustomer);
            return save.getId();
        }
        else{
            return null;
        }
    }

    public Optional<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        if (!customers.isEmpty()) {
            return Optional.of(customers);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<List<Customer>> getCustomerByNameOrVat(String filter) {
       return Optional.of(customerRepo.findByNameContainingIgnoreCaseOrVatNumberContainingIgnoreCaseOrAddresses_addressNameContainingIgnoreCase(filter,filter,filter));
    }

    public void delete(Customer customer) {
        customerRepo.delete(customer);
    }

    public Optional<List<Address>> getAllCustomerAdresses() {
        List<Customer> customers = customerRepo.findAll();
        addressList.clear();
        if (!customers.isEmpty()) {
            for(Customer customer : customers) {
                //Add all workAddresses to the addressList
                customer.getAddresses().stream().filter(address -> (address.getInvoiceAddress() == null) ||  (address.getInvoiceAddress() == false)).forEach(address -> {
                    if((address.getAddressName() != null) && (address.getAddressName().length() > 0)) {
                        address.setCustomerName(customer.getName());
                        addressList.add(address);
                    }
                    else{
                        address.setCustomerName(customer.getName());
                        address.setAddressName(customer.getName());
                        addressList.add(address);
                    }
                });

                //if customer only has a invoiceAddress use this one
                if((customer.getAddresses().size() == 1) && (customer.getAddresses().get(0).getInvoiceAddress() != null) && (customer.getAddresses().get(0).getInvoiceAddress() == true)){
                    Address address = customer.getAddresses().get(0);
                    address.setCustomerName(customer.getName());
                    if((address.getAddressName() == null) || (address.getAddressName().length() == 0)){
                        address.setAddressName(customer.getName());
                    }
                    addressList.add(address);
                }

            }
            return Optional.of(addressList);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<List<Customer>> getCustomerByWorkAddress(Address workAddress) {
        try{
            return Optional.of(customerRepo.findByNameEqualsIgnoreCase(workAddress.getCustomerName()));
        }
        catch(Exception e){
            return Optional.empty();
        }
    }

    public Optional<List<Customer>> getCustomerByWorkAddressName(String workAddressName) {
        try{
            return Optional.of(customerRepo.findByAddresses_AddressName(workAddressName));
        }
        catch(Exception e){
            return Optional.empty();
        }
    }

    public Boolean checkIfWorkAddressNameExists(String workAddressName, String customerId) {
        Optional<List<Customer>> optByAddressesAddressName = Optional.of(customerRepo.findByAddresses_AddressName(workAddressName));
        if(optByAddressesAddressName.isPresent()){
            if((optByAddressesAddressName.get().size() > 1) ){
                return true;
            } else if ((optByAddressesAddressName.get().size() == 1) && ((optByAddressesAddressName.get().getFirst().getId()).equals(customerId))) {
                return false;
            } else if ((optByAddressesAddressName.get().size() == 1) && (!(optByAddressesAddressName.get().getFirst().getId()).equals(customerId)) ) {
                return true;
            } else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public Optional<Customer>findCustomerById(String id) {
        return customerRepo.findById(id);
    }
}

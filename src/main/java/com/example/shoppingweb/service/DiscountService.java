package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Discount;
import com.example.shoppingweb.model.Voucher;
import com.example.shoppingweb.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
    @Autowired
    DiscountRepository discountRepository;

    public List<Discount> getAllDiscounts(){
        return discountRepository.findAll();
    }
    public void addDiscount(Discount discount){
        discountRepository.save(discount);
    }

    public void deleteDiscountByID(Long id){
        discountRepository.deleteById(id);
    }

    public Discount getDiscountByID(Long id) throws UserNotFoundException {
        Optional<Discount> result = discountRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not find any user with id" + id);
    }
}

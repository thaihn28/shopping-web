package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Voucher;
import com.example.shoppingweb.repository.CategoryRepository;
import com.example.shoppingweb.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {

        @Autowired
        VoucherRepository voucherRepository;

        public List<Voucher> getAllVouchers(){
            return voucherRepository.findAll();
        }

        public void addVoucher(Voucher voucher){
            voucherRepository.save(voucher);
        }

        public void deleteVoucherByID(Long id){
            voucherRepository.deleteById(id);
        }

        public Voucher getVoucherByID(Long id) throws UserNotFoundException {
            Optional<Voucher> result = voucherRepository.findById(id);
            if(result.isPresent()){
                return result.get();
            }
            throw new UserNotFoundException("Could not find any user with id" + id);
        }

//        public List<Voucher> findAllVoucherByName(String name){
//            List<Voucher> result =  voucherRepository.findAllByNameContains(name);
//            if(result != null){
//                return result;
//            }else {
//                System.out.println("Can not find any category");
//                return null;
//            }
//        }

}

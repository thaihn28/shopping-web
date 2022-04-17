package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.ItemCartDetail;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ItemCartDetailRepository extends JpaRepository<ItemCartDetail, Long> {
    List<ItemCartDetail> findItemCartDetailByUser(User user);
    ItemCartDetail findItemCartDetailByUserAndProduct(User user, Product product);
    void deleteAllByUser(User user);
}

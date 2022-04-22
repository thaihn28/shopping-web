package com.example.shoppingweb;

import com.example.shoppingweb.model.ItemCartDetail;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.repository.ItemCartDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CartTest {
    @Autowired
    ItemCartDetailRepository itemCartDetailRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addToCart() {
        Product product = entityManager.find(Product.class, 1L);
        //Product product1 = entityManager.find(Product.class, 2);


        User user = entityManager.find(User.class, 1L);
        ItemCartDetail itemCartDetail = new ItemCartDetail();
        itemCartDetail.setUser(user);
        itemCartDetail.setProduct(product);
        itemCartDetail.setQuantity(1);

        itemCartDetailRepository.save(itemCartDetail);

    }
}

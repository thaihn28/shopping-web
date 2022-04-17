package com.example.shoppingweb.api;

import com.example.shoppingweb.dto.AddToCartDTO;
import com.example.shoppingweb.dto.CartItemDTO;
import com.example.shoppingweb.model.*;
import com.example.shoppingweb.repository.ItemCartDetailRepository;
import com.example.shoppingweb.repository.OrderRepository;
import com.example.shoppingweb.repository.ProductRepository;
import com.example.shoppingweb.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ItemCartDetailRepository itemCartDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getLoggedInUser(auth);

        return user;
    }

    @GetMapping("/view-cart")
    public List<CartItemDTO> viewCart() {
        User user = getLoggedInUser();
        List<ItemCartDetail> items = itemCartDetailRepository.findItemCartDetailByUser(user);
        List<CartItemDTO> itemDTOs = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            ItemCartDetail itemCartDetail = items.get(i);
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setProduct(itemCartDetail.getProduct());
            cartItemDTO.setQuantity(itemCartDetail.getQuantity());
            itemDTOs.add(cartItemDTO);
        }
        return itemDTOs;
    }

    @GetMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDTO addToCartDTO) {
        User user = getLoggedInUser();
        Product product = productRepository.findByProductId((long) addToCartDTO.getProductId());
        ItemCartDetail itemCartDetail = itemCartDetailRepository.findItemCartDetailByUserAndProduct(user, product);
        String response;

        if (itemCartDetail == null) {
            ItemCartDetail newItem = new ItemCartDetail();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(addToCartDTO.getQuantity());

            response = "New item added to cart successfully";
            itemCartDetailRepository.save(newItem);
        } else {
            itemCartDetail.setQuantity(itemCartDetail.getQuantity() + addToCartDTO.getQuantity());
            response = "Added to an already exist item in the cart";
            itemCartDetailRepository.save(itemCartDetail);
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/delete-from-cart/{id}")
    public ResponseEntity<?> deleteFromCart(@PathVariable(name = "id") @Min(1) int productId) {
        User user = getLoggedInUser();
        Product product = productRepository.findByProductId((long) productId);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        ItemCartDetail itemCartDetail = itemCartDetailRepository.findItemCartDetailByUserAndProduct(user, product);

        itemCartDetailRepository.delete(itemCartDetail);

        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }

    @GetMapping("/change-quantity/{id}/{quantity}")
    public ResponseEntity<?> changeProductQuantity(@PathVariable("id") int productId, @PathVariable("quantity") int quantity) {
        User user = getLoggedInUser();
        Product product = productRepository.findByProductId((long) productId);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        if (quantity < 1) {
            return new ResponseEntity<>("Quantity must be greater than 1", HttpStatus.NOT_ACCEPTABLE);
        }
        ItemCartDetail itemCartDetail = itemCartDetailRepository.findItemCartDetailByUserAndProduct(user, product);
        if (itemCartDetail == null) {
            return new ResponseEntity<>("Item in cart not found", HttpStatus.NOT_ACCEPTABLE);
        }
        itemCartDetail.setQuantity(quantity);
        itemCartDetailRepository.save(itemCartDetail);

        return new ResponseEntity<>("Changed item quantity successfully", HttpStatus.OK);
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut() {
        User user = getLoggedInUser();
        List<ItemCartDetail> items = itemCartDetailRepository.findItemCartDetailByUser(user);
        List<OrderDetail> orderDetailList = new ArrayList<>();

        if (items.size() == 0) {
            return new ResponseEntity<>("There are no item in the cart to check out", HttpStatus.NOT_ACCEPTABLE);
        }

        Order order = new Order();
        order.setCheckOutTime(new Date());
        order.setUser(user);
        order.setStatus("Waiting");

        for (ItemCartDetail item : items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(item.getProduct());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setOrder(order);
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetails(orderDetailList);
        orderRepository.save(order);

        itemCartDetailRepository.deleteAllByUser(user); //delete after checkout

        return new ResponseEntity<>("Checked out successfully", HttpStatus.OK);
    }

}

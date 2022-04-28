package com.example.shoppingweb.api;

import com.example.shoppingweb.dto.OrderDTO;
import com.example.shoppingweb.dto.OrderItemDTO;
import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.model.OrderDetail;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.repository.OrderRepository;
import com.example.shoppingweb.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderApi {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OrderRepository orderRepository;

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getLoggedInUser(auth);

        return user;
    }

    @GetMapping("/get-all-order")
    public ResponseEntity<?> getAllOrder() {
        List<Order> orders = orderRepository.getAllByUser(getLoggedInUser());
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setApproveTime(order.getApproveTime());
            orderDTO.setConfirmTime(order.getConfirmTime());
            orderDTO.setCheckOutTime(order.getCheckOutTime());
            orderDTO.setRejectTime(order.getRejectTime());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setRealPrice(order.calculateTotalByProduct());
            orderDTO.setStatus(order.getStatus());

            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

            for (OrderDetail orderDetail : order.getOrderDetails()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setProduct(orderDetail.getProduct());
                orderItemDTO.setOrderQuantity(orderDetail.getQuantity());
                orderItemDTOList.add(orderItemDTO);
            }
            orderDTO.setOrderItem(orderItemDTOList);
            orderDTOList.add(orderDTO);
        }

        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<?> rejectOrder(@PathVariable("id") Long id) {
        Order order = orderRepository.getOrderByIdAndUser(id, getLoggedInUser());
        if (order == null) {
            return new ResponseEntity<>("Order not exist", HttpStatus.NOT_FOUND);
        }

        if (order.getStatus().equals("Approved") || order.getStatus().equals("Confirm")) {
            return new ResponseEntity<>("Cannot cancel while the order is approved", HttpStatus.NOT_ACCEPTABLE);
        }
        order.setStatus("Rejected");
        order.setRejectTime(new Date());
        orderRepository.save(order);

        return new ResponseEntity<>("Order has been rejected successfully", HttpStatus.OK);
    }


    @GetMapping("/view-order/{id}")
    public ResponseEntity<?> viewOrder(@PathVariable("id") Long id) {
        Order order = orderRepository.getOrderByIdAndUser(id, getLoggedInUser());
        if (order == null) {
            return new ResponseEntity<>("Order not exist", HttpStatus.NOT_FOUND);
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setApproveTime(order.getApproveTime());
        orderDTO.setConfirmTime(order.getConfirmTime());
        orderDTO.setCheckOutTime(order.getCheckOutTime());
        orderDTO.setRejectTime(order.getRejectTime());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setRealPrice(order.calculateTotalByProduct());
        orderDTO.setStatus(order.getStatus());
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setProduct(orderDetail.getProduct());
            orderItemDTO.setOrderQuantity(orderDetail.getQuantity());
            orderItemDTOList.add(orderItemDTO);
        }
        orderDTO.setOrderItem(orderItemDTOList);

        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }


}

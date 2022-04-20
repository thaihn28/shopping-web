package com.example.shoppingweb.model;

import com.example.shoppingweb.helper.FormatDateHelper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date approveTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date checkOutTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date confirmTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date rejectTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @Transient
    private double totalPrice;

    private String status;

    public double calculateTotal() {
        double total;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getQuantity() * orderDetail.getProduct().getPrice();
        }
        return total = totalPrice;
    }

    public String formatCheckOutDate() throws ParseException {
        return FormatDateHelper.getInstance().format(this.checkOutTime);
    }
    public String formatRejectDate() throws ParseException {
        if (!status.equals("Rejected")) {
            return "Has not been rejected";
        }
        return FormatDateHelper.getInstance().format(this.rejectTime);
    }
    public String formatConfirmDate() throws ParseException {
        if (!status.equals("Confirmed")) {
            return "Has not been confirmed";
        }
        return FormatDateHelper.getInstance().format(this.confirmTime);
    }
    public String approveDate() throws ParseException {
        if (approveTime == null) {
            return "Has not been approved";
        }
        return FormatDateHelper.getInstance().format(this.approveTime);
    }

}

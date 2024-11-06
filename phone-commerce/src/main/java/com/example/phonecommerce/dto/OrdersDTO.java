package com.example.phonecommerce.dto;

import com.example.phonecommerce.models.Orders;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OrdersDTO {
    private long id;
    private String FullName;
    private LocalDateTime OrderCreated;
    private String address;
    private int Total;
    private String Order_status;
    private String phoneNumber;
    private Long userID;
    private String email;
    private int quantity;

    public void convertToDTO(Orders order){

        this.id =order.getId();
        this.Total = order.getOrder_total();
        this.Order_status = order.getOrder_status();
        this.FullName = order.getUserOrder() != null ? order.getUserOrder().getName() : "N/A";
        this.OrderCreated = order.getCreateDate() != null ? order.getCreateDate() : LocalDateTime.now();
        this.phoneNumber = order.getUserOrder() != null ? order.getUserOrder().getPhoneNumber() : "N/A";
        this.userID = order.getUserOrder() != null ? order.getUserOrder().getId() : -1L;
        this.email = order.getUserOrder() != null ? order.getUserOrder().getEmail() : "N/A";
    }

}

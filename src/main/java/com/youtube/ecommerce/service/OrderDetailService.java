package com.youtube.ecommerce.service;

import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.OrderDetailDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.entity.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

   @Autowired
    private OrderDetailDao orderDetailDao;

   @Autowired
    private ProductDao productDao;

   @Autowired
   private UserDao userDao;

   public List<OrderDetail> getAllOrderDetails(){
       List<OrderDetail> orderDetails= new ArrayList<>();
       orderDetailDao.findAll().forEach(

               x -> orderDetails.add(x)
       );
       return orderDetails;
   }

   public List<OrderDetail> getOrderDetails(){
      String currentUser =  JwtRequestFilter.CURRENT_USER;
      User user = userDao.findById(currentUser).get();
     return orderDetailDao.findByUser(user);
   }

    public void placeOrder(OrderInput orderInput){
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for(OrderProductQuantity o: productQuantityList){

            Product product= productDao.findById(o.getProductId()).get();

            String currentUser= JwtRequestFilter.CURRENT_USER;

            User user=userDao.findById(currentUser).get();

            OrderDetail orderDetail=new OrderDetail(
            orderInput.getFullName(),
            orderInput.getFullAddress(),
            orderInput.getContactNumber(),
            orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user
                    );

            orderDetailDao.save(orderDetail);

        }
    }


    public void markOrderAsDelivered(Integer orderId){
       OrderDetail orderDetail= orderDetailDao.findById(orderId).get();
       if(orderDetail != null){
           orderDetail.setOrderStatus("Delivered");
           orderDetailDao.save(orderDetail);
       }
    }


}

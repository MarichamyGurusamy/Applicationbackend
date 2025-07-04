package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    public Product addNewProduct(Product product)
    {
        return productDao.save(product);
    }
    public List<Product> getAllProducts(int pageNumer){
        Pageable pageable = PageRequest.of(pageNumer,8);
        return(List<Product>) productDao.findAll(pageable);
    }

    public Product getProductDetailsById(Integer productId){
      return productDao.findById(productId).get();
    }


    public void deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {

        if(isSingleProductCheckout){
            //We are going to single product
            List<Product> list = new ArrayList<>();
          Product product =  productDao.findById(productId).get();
          list.add(product);
          return list;

        }else{//we are going to checkout with entire cart

        }
        return new ArrayList<>();
    }
}

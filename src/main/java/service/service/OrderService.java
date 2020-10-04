package service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.model.Orders;
import service.repo.OrderRepo;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public Orders save(Orders orders) {
            return orderRepo.save(orders);
    }

    public Orders update(Orders ordersFromDb, Orders orders) {
        BeanUtils.copyProperties(orders, ordersFromDb, "id");
        return orderRepo.save(ordersFromDb);
    }

    public List<Orders> findAll() {
        return orderRepo.findAll();
    }

    public void delete(Orders orders) {
        orderRepo.delete(orders);
    }

    public Orders getOrderById(Long id){
        return orderRepo.findById(id).get();
    }

}

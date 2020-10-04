package service.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.model.Orders;
import service.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public Orders getOne(@PathVariable("id") Orders orders){
        return orders;
    }

    @GetMapping
    public List<Orders> getListOrders(){
        return orderService.findAll();
    }

    @PostMapping
    public Orders save(@RequestBody Orders orders){
        return orderService.save(orders);
    }

    @PutMapping("{id}")
    public Orders update(@PathVariable("id") Orders ordersFromDb, @RequestBody Orders orders){
        BeanUtils.copyProperties(orders, ordersFromDb, "id");
        return orderService.save(ordersFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Orders orders){
        orderService.delete(orders);
    }
}

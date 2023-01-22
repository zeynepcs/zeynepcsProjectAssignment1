package se.jensenyh.javacourse.saltmerch.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.jensenyh.javacourse.saltmerch.backend.model.CartItem;
import se.jensenyh.javacourse.saltmerch.backend.repository.CartRepository;

import java.util.List;

@Service


public class CartService{
@Autowired

CartRepository db;
    public List<CartItem> getCartService()
    {
        return db.selectAllItems();
    }
    public int addCartService(CartItem item)
    {
        return db.insertOrIncrementItem(item);
    }
    public int removeCartService(CartItem item)
    {
        return db.deleteOrDecrementItem(item);
    }
}

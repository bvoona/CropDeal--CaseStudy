package com.cropdeal.cartservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cropdeal.cartservice.model.Cart;
import com.cropdeal.cartservice.model.Crop;
import com.cropdeal.cartservice.model.Items;
import com.cropdeal.cartservice.repository.CartRepository;
import com.cropdeal.cartservice.service.CartService;


	@RestController
	@RequestMapping("/cart")
	public class CartController
	{
		@Autowired
	     CartService cartService;
		@Autowired
		CartRepository cartRepository;
		 @Autowired
		 RestTemplate restTemp;
		
		
		
		 Logger logger=LoggerFactory.getLogger(CartController.class);
		    
		    
	    public CartController(CartService cartService)
	    {
	        this.cartService=cartService;
	    }
	    public CartController()
	    {
	        
	    }
	   
	    @GetMapping("/getallcarts")
	    public ResponseEntity<List<Cart>> getAllCarts()
	    {
	        return ResponseEntity.ok(cartService.getallcarts());
	    }
		
	    @PostMapping("/addingproducttocart/{cartId}/{id}")
	    public ResponseEntity<Cart> addProductToCart(@PathVariable int cartId,@PathVariable String id)
	    {
			Crop crop = restTemp.getForObject("http://CropService/crop/getCropById/"+id, Crop.class);
	    logger.info(""+crop);
	    if(cartRepository.existsById(cartId))
	    {
	    	Cart oldCart=cartRepository.findById(cartId);
	    	List<String> idList=new ArrayList<>();
	    	List<Items> oldItem3=oldCart.getItems();
	    	for(Items i : oldItem3)
	    	{
	    		idList.add(i.getId());
	    	}
	    	if(idList.contains(crop.getId()))
	    	{
	    		logger.info("in if method");
	    		List<Items> oldItem2=oldCart.getItems();
	    		for(Items i : oldItem2 )
	    		{
	    			if(i.getId().equals(id))
	    			{
	    				i.setQuantity(i.getQuantity()+1);
	    			}
	    		}
	    		double price=0;
	    		for(Items i : oldItem2)
	    		{
	    			price= price+ i.getPrice()*i.getQuantity();
	    		}
	    		oldCart.setTotalPrice(price);
	    		return new ResponseEntity<> (cartService.addCart(oldCart),HttpStatus.CREATED);
	    		}
	    	else
	    	{
	    		Items items=new Items();
	    		items.setId(id);
	    		items.setPrice(crop.getPrice());
	    		items.setProductName(crop.getCropName());
	    		items.setQuantity(1);
	    		//items.setImage(crop.getImage());
	    		List<Items> oldItems=oldCart.getItems();
	    		oldItems.add(items);
	    		oldCart.setItems(oldItems);
	    		double price=0;
	    		for(Items i : oldItems)
	    		{
	    			price = price+i.getPrice()*i.getQuantity();
	    		}
	    		oldCart.setTotalPrice(price);
	    		return new ResponseEntity<> (cartService.addCart(oldCart),HttpStatus.CREATED);
	    	}
	    }
	    	else
	    	{
	    		Cart cart=new Cart();
	    		cart.setCartId(cartId);
	    		Items items=new Items();
	    		items.setId(id);
	    		items.setPrice(crop.getPrice());
	    		items.setProductName(crop.getCropName());
	    		items.setQuantity(1);
	    		//items.setImage(crop.getImage());
	    		List<Items> list=new ArrayList<>();
	    		list.add(items);
	    		cart.setItems(list);
	    		double price=0;
	    		for(Items i : list )
	    		{
	    			price=price+ i.getPrice()*i.getQuantity();
	    		}
	    		cart.setTotalPrice(price);
	    		return new ResponseEntity<> (cartRepository.save(cart),HttpStatus.CREATED);
	    		
	    		}
	        } 
	    @GetMapping("/{cartId}")
	    public ResponseEntity<Cart> getCartById(@PathVariable int cartId)
	    {
	    	return new ResponseEntity<> (cartService.getCartById(cartId),HttpStatus.CREATED);
	    }

	  @PutMapping("/delete/item/{id}/{cartId}")
	    public Cart deleteCartItem(@PathVariable int cartId,@PathVariable String id)
	    {
			Crop crop = restTemp.getForObject("http://CropService/crop/getCropById/"+id, Crop.class);
	    	Cart cart2=cartService.getCartById(cartId);
	    	List<Items> list=new ArrayList<>();
	    	list=cart2.getItems();
	    	System.out.println(list);
	    	list.removeIf(i->(i.getId().equals(id)));
	    	cart2.setItems(list);
	    	double price=0;
	    	for(Items i : list)
	    	{
	    		price=price+i.getPrice()*i.getQuantity();
	    		
	    	}
	    	cart2.setTotalPrice(price);
	    	return cartService.updateCart(cart2.getCartId(),cart2);
	    	 }
	    @PutMapping("/increaseQuant/{cartId}/{id}")
	    public Cart increaseItem(@PathVariable int cartId,@PathVariable String id )
	    {
	    	Cart cart=cartService.getCartById(cartId);
	    	List<Items> oldItem3=cart.getItems();
	    	for(Items i : oldItem3 )
	    	{
	    		if(i.getId().equals(id))
	    		{
	    			i.setQuantity(i.getQuantity()+1);
	    		
	    		}
	    	}
	    	double price=0;
	    	for(Items i : oldItem3)
	    	{
	    		price=price+i.getPrice()*i.getQuantity();
	    		
	    	}
	    	cart.setTotalPrice(price);
	    	return cartService.updateCart(cartId,cart);
	    	 }
	    @PutMapping("/decreaseQuant/{id}/{cartId}")
	    public Cart decreaseItem(@PathVariable String id,@PathVariable int cartId)
	    {
	    	Cart cart=cartService.getCartById(cartId);
	    	List<Items> oldItem3=cart.getItems();
	    	for(Items i: oldItem3)
	       {
	    		if(i.getId().equals(id))
	    		{
	    			i.setQuantity(i.getQuantity()-1);
	    		}
	       }
	    	double price=0;
	    	for(Items i: oldItem3)
	    	{
	    		price=price+i.getPrice()*i.getQuantity();
	    	}
	    	cart.setTotalPrice(price);
	    	return cartService.updateCart(cartId, cart);
	    	
	    }
	    @DeleteMapping("/delete/{cartId}")
	    public void deleteCart(@PathVariable int cartId)
	    {
	    	cartService.deleteCartById(cartId);
	    }
	  
	    }
	


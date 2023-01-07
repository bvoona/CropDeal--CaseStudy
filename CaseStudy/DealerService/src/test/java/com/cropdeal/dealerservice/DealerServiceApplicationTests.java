package com.cropdeal.dealerservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cropdeal.dealerservice.controller.DealerController;
import com.cropdeal.dealerservice.exception.DealerNotFoundException;
import com.cropdeal.dealerservice.repository.DealerRepository;
import com.cropdeal.dealerservice.service.DealerServiceImpl;
import com.cropdeal.dealerservice.model.Dealer;
@SpringBootTest
class DealerServiceApplicationTests {

	@Autowired
    private DealerServiceImpl dealercontroller;
    

  @MockBean
    private DealerRepository dealerrepo;
    
         
    @Test
    public void getdealerTest() {
        when(dealerrepo.findAll()).thenReturn(Stream.of(new Dealer("1","abc","abc@gmail.com","12412414",null)).collect(Collectors.toList()));
        assertEquals(1, dealercontroller.findAll().size());
    }
    
    @Test
    public void getdealerByIdTest() throws DealerNotFoundException {
    	String id= "1";
    	try {
    		dealercontroller.getDealerById(id);
    	}catch (DealerNotFoundException e) {
    		System.out.println(e);
		} 
        
    }

    @Test
    public void adddealerTest() {
        Dealer dealer = new Dealer("1","abc","abc@gmail.com","12412414",null);
        when(dealerrepo.insert(dealer)).thenReturn(dealer);
        Dealer res=dealercontroller.addDealer(dealer);
        assertEquals(dealer.getDealerId(),res.getDealerId());
        //assertEquals(dealer.getId(),res.getId());
    }

  @Test
    public void deletedealerbyidTest() {
        String dealer = Dealer("1","abc","abc@gmail.com","12412414",null);
        dealercontroller.deleteById("1");
        verify(dealerrepo, times(1)).deleteById("1");
    }

  
private String Dealer(String i, String string, String string2, String string3, Object object) {
	return null;
}
        
  
}



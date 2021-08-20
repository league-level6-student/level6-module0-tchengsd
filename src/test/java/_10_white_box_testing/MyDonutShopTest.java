package _10_white_box_testing;

import _09_intro_to_white_box_testing.models.DeliveryService;
import _09_intro_to_white_box_testing.models.Order;
import _10_white_box_testing.models.BakeryService;
import _10_white_box_testing.models.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MyDonutShopTest {

    MyDonutShop myDonutShop;
    
    @Mock
    DeliveryService deliveryService;
    
    @Mock
    PaymentService paymentService;

    @Mock
    BakeryService bakeryService;
    
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	myDonutShop = new MyDonutShop(paymentService, deliveryService, bakeryService);
    }

    @Test
    void itShouldTakeDeliveryOrder() throws Exception {
    	//given
    	myDonutShop.openForTheDay();
    	System.out.println(bakeryService.getDonutsRemaining());
    	Order order = new Order("Robert", "8675309", 1, 24.99, "5678 9456 2342 0690", true);
        //when
    	myDonutShop.takeOrder(order);
        //then
    	verify(deliveryService, times(1)).scheduleDelivery(order);
    }

    @Test
    void givenInsufficientDonutsRemaining_whenTakeOrder_thenThrowIllegalArgumentException() {
        //given
    	Order order = new Order("Wario", "1021199200", 100, 100, "1992 0000 1021 0690", true);
        //when    	
    	myDonutShop.openForTheDay();
        //then
    	Throwable exceptionThrown = assertThrows(Exception.class, () -> myDonutShop.takeOrder(order));
        assertEquals("Insufficient donuts remaining", exceptionThrown.getMessage());
        verify(bakeryService, never()).removeDonuts(100);
    }

    @Test
    void givenNotOpenForBusiness_whenTakeOrder_thenThrowIllegalStateException(){
        //given
    	Order order = new Order("Robert", "8675309", 1, 24.99, "5678 9456 2342 0690", true);
        //when
    	myDonutShop.closeForTheDay();
        //then
    	Throwable exceptionThrown = assertThrows(Exception.class, () -> myDonutShop.takeOrder(order));
        assertEquals("Sorry we're currently closed", exceptionThrown.getMessage());
        verify(bakeryService, never()).removeDonuts(1);
    }
}

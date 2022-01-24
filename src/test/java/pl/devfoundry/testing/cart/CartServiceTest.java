package pl.devfoundry.testing.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.devfoundry.testing.order.Order;
import pl.devfoundry.testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartHandler cartHandler;
    @Captor
    private ArgumentCaptor<Cart> argumentCaptor;


    @Test
    void processCartShouldSendToPrepare() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

//        when
        Cart resultCart = cartService.processCart(cart);

//        then
        verify(cartHandler).sendToPrepare(cart);
//        analogiczny zapis do tego powyżej
        then(cartHandler).should().sendToPrepare(cart);

        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void processCartShouldNotSendToPrepare() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(false);

//        when
        Cart resultCart = cartService.processCart(cart);

//        then
        verify(cartHandler, never()).sendToPrepare(cart);
//        analogiczny zapis do powyższego
        then(cartHandler).should(never()).sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));

    }

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(any())).willReturn(false);
//        powyżej jeśli canHandleCart zostanie wywołane z dowolonym argumentem to zwrócone zostanie false
        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(false);
//        powyżej tylko canHandleCart z argumentem z klasy Cart
//        mamy też anyBoolean, anyInt, które możemy używać

//        when
        Cart resultCart = cartService.processCart(cart);

//        then
        verify(cartHandler, never()).sendToPrepare(any());
        verify(cartHandler, never()).sendToPrepare(any(Cart.class));
//        poniżej analogiczny zapis do powyższego
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class));
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));

    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
//        CartHandler cartHandler = mock(CartHandler.class);

        given(cartHandler.canHandleCart(cart)).willReturn(true, false, false, true);

//        then
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));

    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(true);

//        when
        Cart resultCart = cartService.processCart(cart);

//        then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }


    @Test
    void canHandleCartShouldThrowException() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willThrow(IllegalStateException.class);

//        when
//        then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));

    }

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

//        when
        Cart resultCart = cartService.processCart(cart);

//        then
        verify(cartHandler).sendToPrepare(argumentCaptor.capture());
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void shouldDoNothingWHenProcessCart() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

//        doNothing().when(cartHandler).sendToPrepare(cart);
//        lub
//        willDoNothing().given(cartHandler).sendToPrepare(cart);
//        lub
        willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart);

//        when
        Cart resultCart = cartService.processCart(cart);

//        then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void shouldAnswerWhenProcessCart() {

//        given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        doAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);

        when(cartHandler.canHandleCart(cart)).then(i -> {
            Cart argumentCart = i.getArgument(0);
            argumentCart.clearCart();
            return true;
        });

//        willAnswer(invocationOnMock -> {
//            Cart argumentCart = invocationOnMock.getArgument(0);
//            argumentCart.clearCart();
//            return true;
//        }).given(cartHandler).canHandleCart(cart);
//
//        given(cartHandler.canHandleCart(cart)).will(i -> {
//            Cart argumentCart = i.getArgument(0);
//            argumentCart.clearCart();
//            return true;
//        });

//        when
        Cart resultCart = cartService.processCart(cart);

//        then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders().size(), equalTo(0));

    }

//    @Test
//    void deliveryShouldBeFree() {
//
////        given
//        Cart cart = new Cart();
//        cart.addOrderToCart(new Order());
//        cart.addOrderToCart(new Order());
//        cart.addOrderToCart(new Order());
//
//        CartHandler cartHandler = mock(CartHandler.class);
//        given(cartHandler.isDeliveryFree(cart)).willCallRealMethod();
////        lub
////        doCallRealMethod().when(cartHandler).isDeliveryFree(cart);
//
////        when
//        boolean isDeliveryFree = cartHandler.isDeliveryFree(cart);
//
////        then
//        assertTrue(isDeliveryFree);
//    }
}

package com.app.application.service.orders;

import com.app.application.dto.order.CreateOrderDto;
import com.app.application.validator.CreateOrderDtoValidator;
import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.provider.OrdersProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
class AddOrderTest {

    @Mock
    CustomerRepositoryDb customerRepository;

    @Mock
    CreateOrderDtoValidator createOrderDtoValidator;

    @Mock
    OrdersProvider ordersProvider;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @Test
    @DisplayName("When customer exists and createOrderDto has different data and the same email as existing customer")
    void test1() {
        var createOrderDto = new CreateOrderDto("AA", "A", 25, "a@gmail.com",
                1L, 1);

        when(customerRepository.findByEmail("a@gmail.com"))
                .thenReturn(Optional.of(CustomerEntity
                        .builder()
                        .name("A")
                        .surname("A")
                        .email("a@gmail.com")
                        .age(25)
                        .build()));

        assertThatThrownBy(() -> ordersService.addOrder(createOrderDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Another data is associated with given email");
    }
}

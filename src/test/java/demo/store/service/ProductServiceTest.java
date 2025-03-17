package demo.store.service;

import demo.store.model.Product;
import demo.store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DataJpaTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindAll() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", "Apple", 100.),
                new Product(2L, "Product 2", "Banana", 200.)
        );
        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void testFindById(Long id) {
        Product product = new Product(id, "Product " + id, "Apple", 100.);
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.findById(id);

        assertEquals(product, result);
        Mockito.verify(productRepository, Mockito.times(1)).findById(id);
    }

    @ParameterizedTest
    @MethodSource("productProvider")
    void testFindById(Product product) {
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product result = productService.findById(product.getId());

        assertEquals(product, result);
        Mockito.verify(productRepository, Mockito.times(1)).findById(product.getId());
    }

    private static Stream<Arguments> productProvider() {
        return Stream.of(
                Arguments.of(new Product(1L, "Product 1", "Apple", 100.)),
                Arguments.of(new Product(2L, "Product 2", "Banana", 200.))
        );
    }

    @Test
    @WithMockUser(username = "user")
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", "Apple", 100.),
                new Product(2L, "Product 2", "Banana", 200.)
        );
        Mockito.when(productService.findAll()).thenReturn(products);

        mockMvc.perform(
                        get("/api/products")
                                .contentType("application/json")
                                .accept("application/json")
                )
                .andExpect(status().isOk())
                .andDo(
                        result -> System.out.println(result.getResponse().getContentAsString())
                )
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }
}
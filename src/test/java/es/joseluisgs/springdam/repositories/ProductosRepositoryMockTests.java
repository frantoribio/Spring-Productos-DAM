package es.joseluisgs.springdam.repositories;

import es.joseluisgs.springdam.models.Producto;
import es.joseluisgs.springdam.repositories.productos.ProductosRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Test unitario del repositorio mockeado
// Esto y "nada" es lo mismo... Mejor hacerlo con JPA

@SpringBootTest
// De esta manera levanto la base de datos de prueba en cada test
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductosRepositoryMockTests {

    private final Producto producto = Producto.builder()
            .nombre("Producto de prueba")
            .id(1L)
            .precio(10.0)
            .stock(10)
            .build();


    // Repositorio a testar mockeado en el contexto de Spring
    @MockBean
    private ProductosRepository productosRepository;

    @Test
    @Order(1)
    public void save() {
        // Arrange
        Mockito.when(productosRepository.save(producto)).thenReturn(producto);
        // Act
        var res = productosRepository.save(producto);
        // Assert
        assertAll(
                () -> assertEquals(producto, res),
                () -> assertEquals(producto.getId(), res.getId()),
                () -> assertEquals(producto.getNombre(), res.getNombre()),
                () -> assertEquals(producto.getPrecio(), res.getPrecio()),
                () -> assertEquals(producto.getStock(), res.getStock())
        );

        Mockito.verify(productosRepository, Mockito.times(1)).save(producto);
    }

    @Test
    @Order(2)
    public void findById() {
        // Arrange
        Mockito.when(productosRepository.findById(producto.getId()))
                .thenReturn(java.util.Optional.of(producto));
        // Act
        var res = productosRepository.findById(producto.getId()).get();
        // Assert
        assertAll(
                () -> assertEquals(producto, res),
                () -> assertEquals(producto.getId(), res.getId()),
                () -> assertEquals(producto.getNombre(), res.getNombre()),
                () -> assertEquals(producto.getPrecio(), res.getPrecio()),
                () -> assertEquals(producto.getStock(), res.getStock())
        );

        Mockito.verify(productosRepository, Mockito.times(1))
                .findById(producto.getId());
    }

    @Test
    @Order(3)
    public void findAll() {
        // Arrange
        Mockito.when(productosRepository.findAll())
                .thenReturn(List.of(producto));
        // Act
        var res = productosRepository.findAll();
        // Assert
        assertAll(
                () -> assertEquals(List.of(producto), res),
                () -> assertEquals(producto.getId(), res.get(0).getId()),
                () -> assertEquals(producto.getNombre(), res.get(0).getNombre()),
                () -> assertEquals(producto.getPrecio(), res.get(0).getPrecio()),
                () -> assertEquals(producto.getStock(), res.get(0).getStock())
        );

        Mockito.verify(productosRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @Order(4)
    public void update() {
        // Arrange
        Mockito.when(productosRepository.save(producto))
                .thenReturn(producto);
        // Act
        var res = productosRepository.save(producto);
        // Assert
        assertAll(
                () -> assertEquals(producto, res),
                () -> assertEquals(producto.getId(), res.getId()),
                () -> assertEquals(producto.getNombre(), res.getNombre()),
                () -> assertEquals(producto.getPrecio(), res.getPrecio()),
                () -> assertEquals(producto.getStock(), res.getStock())
        );

        Mockito.verify(productosRepository, Mockito.times(1))
                .save(producto);
    }

    @Test
    @Order(5)
    public void delete() {
        // Arrange
        Mockito.doNothing().when(productosRepository).delete(producto);
        // Act
        productosRepository.delete(producto);
        // Assert
        Mockito.verify(productosRepository, Mockito.times(1))
                .delete(producto);
    }
}

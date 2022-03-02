package es.joseluisgs.springdam.repositories;

import es.joseluisgs.springdam.models.Producto;
import es.joseluisgs.springdam.repositories.productos.ProductosRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

// Test de integración del repositorio
// La peor manera, porque si ya probamos integración lo hacemos desde el controlador "final"
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Levanto la BBDD en cada test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductosRepositoryIntegrationTests {

    private final Producto producto = Producto.builder()
            .nombre("Producto de prueba")
            .id(-1L)
            .precio(10.0)
            .stock(10)
            .build();

    // Repositorio a testear "de verdad" es integracion
    @Autowired
    private ProductosRepository productosRepository;

    @Test
    @Order(1)
    public void save() {
        Producto res = productosRepository.save(producto);

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(producto.getNombre(), res.getNombre()),
                () -> assertEquals(producto.getPrecio(), res.getPrecio()),
                () -> assertEquals(producto.getStock(), res.getStock())
        );
    }

    @Test
    @Order(2)
    public void getAllProductos() {
        // Porque yo he metido los datos en la BBDD
        assertTrue(productosRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    public void getProductoById() {
        var prod = productosRepository.save(producto);
        var res = productosRepository.findById(prod.getId()).get();

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(producto.getNombre(), res.getNombre()),
                () -> assertEquals(producto.getPrecio(), res.getPrecio()),
                () -> assertEquals(producto.getStock(), res.getStock())
        );
    }

    @Test
    @Order(4)
    public void updateProducto() {
        var prod = productosRepository.save(producto);
        prod = productosRepository.findById(prod.getId()).get();
        prod.setNombre("Producto de prueba modificado");

        var res = productosRepository.save(prod);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals("Producto de prueba modificado", res.getNombre()),
                () -> assertEquals(producto.getPrecio(), res.getPrecio()),
                () -> assertEquals(producto.getStock(), res.getStock())
        );
    }

    @Test
    @Order(5)
    public void deleteProducto() {
        Producto res = productosRepository.save(producto);
        res = productosRepository.findById(res.getId()).get();

        productosRepository.delete(res);
        
        assertNull(productosRepository.findById(res.getId()).orElse(null));

    }
}

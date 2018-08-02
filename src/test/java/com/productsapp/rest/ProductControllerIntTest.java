package com.productsapp.rest;

import com.productsapp.domain.entities.Product;
import com.productsapp.domain.repository.ProductRepository;
import com.productsapp.domain.service.ProductService;
import com.productsapp.domain.service.dto.ProductDTO;
import com.productsapp.domain.service.mapper.ProductMapper;
import com.productsapp.util.TestUtils;
import com.productsapp.validation.RestExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.productsapp.util.TestUtils.convertObjectToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductControllerIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private RestExceptionHandler exceptionHandler;

    @Autowired
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductController productController = new ProductController(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .alwaysDo(print())
                .setControllerAdvice(exceptionHandler)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .description(DEFAULT_DESCRIPTION);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        double databaseSizeBeforeCreate = productRepository.count();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc.perform(post(ProductController.ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize((int) (databaseSizeBeforeCreate + 1));
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        Product saved = productRepository.saveAndFlush(product);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(saved.getId());
        ProductDTO productDTO = productMapper.toDto(product);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post(ProductController.ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setName(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);

        restProductMockMvc.perform(post(ProductController.ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setPrice(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);

        restProductMockMvc.perform(post(ProductController.ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get(ProductController.ENDPOINT + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content[*].id").value(hasItem(product.getId().intValue())))
                .andExpect(jsonPath("$.content[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.content[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.content[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get(ProductController.ENDPOINT + "/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(product.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ProductController.ENDPOINT + "/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
                .name(UPDATED_NAME)
                .price(UPDATED_PRICE)
                .description(UPDATED_DESCRIPTION);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc.perform(put(ProductController.ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductMockMvc.perform(put(ProductController.ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isNotFound());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtils.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtils.equalsVerifier(ProductDTO.class);
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        ProductDTO productDTO2 = new ProductDTO();
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO2.setId(productDTO1.getId());
        assertThat(productDTO1).isEqualTo(productDTO2);
        productDTO2.setId(2L);
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO1.setId(null);
        assertThat(productDTO1).isNotEqualTo(productDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productMapper.fromId(null)).isNull();
    }

}

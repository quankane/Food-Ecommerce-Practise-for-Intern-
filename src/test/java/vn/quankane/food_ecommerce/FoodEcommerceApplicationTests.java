package vn.quankane.food_ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import vn.quankane.food_ecommerce.config.DotenvInitializer;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class FoodEcommerceApplicationTests {

	@Test
	void contextLoads() {
	}

}

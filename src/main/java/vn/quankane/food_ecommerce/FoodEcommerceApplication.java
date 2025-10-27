package vn.quankane.food_ecommerce;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.hibernate.cfg.Environment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.quankane.food_ecommerce.config.properties.AdminInfoProperties;
import vn.quankane.food_ecommerce.domain.entity.cart.Cart;
import vn.quankane.food_ecommerce.domain.entity.user.Role;
import vn.quankane.food_ecommerce.domain.entity.user.User;
import vn.quankane.food_ecommerce.repository.CartRepository;
import vn.quankane.food_ecommerce.repository.UserRepository;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "vn.quankane.food_ecommerce")
@EnableConfigurationProperties({AdminInfoProperties.class})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodEcommerceApplication {

	UserRepository userRepository;

	CartRepository cartRepository;

	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		ConfigurableEnvironment env = SpringApplication.run(FoodEcommerceApplication.class, args).getEnvironment();

		String appName = env.getProperty("spring.application.name");
		if (appName != null) {
			appName = appName.toUpperCase();
		}
		String port = env.getProperty("server.port");
		log.info("-------------------------START {} Application------------------------------", appName);
		log.info("   Application         : {}", appName);
		log.info("   Url swagger-ui      : http://localhost:{}/swagger-ui.html", port);
		log.info("-------------------------START SUCCESS {} Application------------------------------", appName);
	}

	@Bean
	CommandLineRunner init(AdminInfoProperties adminInfoProperties) {
		return args -> {
			if (userRepository.count() == 0) {
				User admin = User.builder()
						.username(adminInfoProperties.getUsername())
						.password(passwordEncoder.encode(adminInfoProperties.getPassword()))
						.firstName(adminInfoProperties.getFirstName())
						.lastName(adminInfoProperties.getLastName())
						.email(adminInfoProperties.getEmail())
						.role(Role.ADMIN)
						.build();

				userRepository.save(admin);

				Cart cart = new Cart();
				cart.setUser(admin);
				admin.setCart(cart);

				cartRepository.save(cart);

				log.info("admin created successful with name: {} and password = {}", admin.getUsername(), admin.getPassword());
			}
		};
	}
}

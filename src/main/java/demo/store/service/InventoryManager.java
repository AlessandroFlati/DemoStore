package demo.store.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class InventoryManager {
}

/*
    This is exactly equivalent to the following Java Configuration file:
    @Configuration
    public class InventoryManagerConfig {
        @Bean
        @Scope("singleton")
        public InventoryManager inventoryManager() {
            return new InventoryManager();
        }
    }
 */
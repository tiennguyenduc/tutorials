package com.tnd;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tnd.model.Category;
import com.tnd.model.Product;
import com.tnd.producer.Producer;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    @Autowired
    Producer producer;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        Product iphoneX = new Product();
        iphoneX.setName("IPhone X");

        Product note8 = new Product();
        note8.setName("Note 8");

        Category smartPhone = new Category();
        smartPhone.setName("Smart Phone");
        smartPhone.setProducts(Arrays.asList(iphoneX, note8));

        iphoneX.setCategory(smartPhone);
        note8.setCategory(smartPhone);

        producer.produce(smartPhone);

    }
}

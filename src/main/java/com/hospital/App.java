package com.hospital;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration("/applicationContext.xml")
public class App {

	@Autowired
	private EntityManager entityManager;

	public static void main(String[] args) {
		App app = SpringApplication.run(App.class).getBean(App.class);
		app.nativeQuery("SHOW TABLES");
		app.nativeQuery("SHOW COLUMNS from t_patient");
		app.nativeQuery("SHOW COLUMNS from t_urgency");
		app.nativeQuery("SHOW COLUMNS from t_supply");

	
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	private void nativeQuery(String s) {
		System.out.printf("-----------------------------%n'%s'%n", s);
		Query query = entityManager.createNativeQuery(s);
		List list = query.getResultList();
		for (Object o : list) {
			if (o instanceof Object[]) {
				System.out.println(Arrays.toString((Object[]) o));
			} else {
				System.out.println(o);
			}
		}

	}
}
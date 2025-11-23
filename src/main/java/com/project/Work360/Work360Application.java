package com.project.Work360;

import com.project.Work360.oracle.procedures.UsuarioDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement; 


@EnableCaching
@SpringBootApplication
@EnableTransactionManagement 
public class Work360Application {

	public static void main(String[] args) {
	SpringApplication.run(Work360Application.class, args);
		System.out.println("Bem vindo ao Hub Work360");
	}

	@Bean
	public CommandLineRunner testOracleProcedure(UsuarioDAO usuarioDAO) {
		return args -> {
			System.out.println("\n--- INICIANDO EXECUÇÃO DA PROCEDURE ORACLE ---");
			usuarioDAO.inserirUsuario("Usuário via Procedure", "procedure.user@work360.com", "senha123");
			System.out.println("--- PROCEDURE EXECUTADA COM SUCESSO ---\n");
		};
	}
	// http://localhost:8080/swagger-ui/index.html
}

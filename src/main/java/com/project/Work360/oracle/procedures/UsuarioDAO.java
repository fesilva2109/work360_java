package com.project.Work360.oracle.procedures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioDAO(@Qualifier("oracleJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //Insere um novo usuário no banco de dados Oracle chamando a procedure PKG_USUARIOS.PRC_INS_USUARIO.
 
    @Transactional("oracleTransactionManager")
    public void inserirUsuario(String nome, String email, String senha) {
        // A string SQL para chamar a procedure.
        String sql = "{call PKG_USUARIOS.PRC_INS_USUARIO(?, ?, ?)}";

        // O JdbcTemplate gerencia a conexão, o statement e o tratamento de exceções.
        jdbcTemplate.update(sql, nome, email, senha);
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ormhibernate;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alunoccc
 */
@Entity
public class Dependente {

    private int id;
    private String nome;
    private Date DataNascimento;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataNascimento")
    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date DataNascimento) {
        this.DataNascimento = DataNascimento;
    }
}

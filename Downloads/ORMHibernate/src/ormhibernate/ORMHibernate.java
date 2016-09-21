/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ormhibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author alunoccc
 */
public class ORMHibernate {

    // TODO code application logic here
    /* public static void main(String[] args) {
        Dependente d = new Dependente(); 
        d.setDataNascimento(new Date());
        d.setNome("João");
                
        
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin(); //inicia uma transação
        //associa o objeto ao entityManager fazendo com que ele passe a ser gerenciado
        entityManager.persist(d);
        //confirma a transação e persiste o objeto no banco
        entityManager.getTransaction().commit();
        entityManager.close(); //fecha o entityManager
     */
    public static void inserirDependente(Dependente dependente) {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("persistence");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            entityManager.persist(dependente);

            entityManager.getTransaction().commit();

        } catch (Exception ex) {

            entityManager.getTransaction().rollback();

            throw ex;

        } finally {

            entityManager.close();

        }

    }

    public static void selecionarTodosDependentes() {

        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("persistence");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaQuery<Dependente> criteria
                = entityManager.getCriteriaBuilder().createQuery(Dependente.class);

        criteria.select(criteria.from(Dependente.class));

        List<Dependente> dependentes = entityManager.createQuery(criteria).getResultList();

        for (Dependente dependente : dependentes) {

            System.out.println("Id: " + dependente.getId() + ", Nome: " + dependente.getNome() + ", DataNascimento: " + dependente.getDataNascimento());

        }

        entityManager.close();

    }

    public static void excluirDependente(int idDependente) {

        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("persistence");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            Dependente dependente = entityManager.find(Dependente.class, idDependente);

            if (dependente != null) {

                entityManager.remove(dependente);

            } else {
                System.out.println("Não foi possível encontrar o dependente com o id '" + idDependente
                        + "'");

            }

            entityManager.getTransaction().commit();

        } catch (Exception ex) {

            entityManager.getTransaction().rollback();

            throw ex;

        } finally {

            entityManager.close();

        }

    }

    public static void atualizarDependente(Dependente dependente) {

        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("persistence");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            Dependente dependentePersistido = entityManager.find(Dependente.class, dependente.getId());

            if (dependentePersistido != null) {
                dependentePersistido.setId(dependente.getId());
                dependentePersistido.setNome(dependente.getNome());
                dependentePersistido.setDataNascimento(dependente.getDataNascimento());

            } else {

                System.out.println("Não foi possível encontrar o dependente com o id '"
                        + dependente.getId() + "'");
            }

            entityManager.getTransaction().commit();

        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

    }

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);

        int opcao = 0;

        do {

            System.out.println("***SISTEMA DE GERENCIAMENTO DE DEPENDENTES***");

            System.out.println(" DIGIETE:\n"
                    + " 1 - Listar Dependentes\n"
                    + " 2 - Para Cadastrar Dependente\n"
                    + " 3 - Atualizar Dependente\n"
                    + " 4 - Excluir Dependente\n"
                    + " 5 - Para Sair\n");

            opcao = scanner.nextInt();

            scanner.nextLine();

            switch (opcao) {

                case 1: {
                    ORMHibernate.selecionarTodosDependentes();
                    break;
                }

                case 2: {
                    try {
                        Dependente d = new Dependente();

                        System.out.println("Digite o nome do Dependente: ");
                        d.setNome(scanner.nextLine());                  

                        System.out.println("A data de nascimento do Dependente (dd/MM/yyyy): ");
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        d.setDataNascimento(new java.sql.Date(format.parse(scanner.nextLine()).getTime()));
                        ORMHibernate.inserirDependente(d);
                        System.out.println("id: " + d.getId());

                    } catch (ParseException ex) {
                        System.out.println(ex.toString());
                    }
                    break;

                }

                case 3: {

                    try {

                        Dependente d = new Dependente();
                        System.out.println("Digite o Id do Dependente: ");
                        d.setId(scanner.nextInt());
                        scanner.nextLine();
                        System.out.println("Digite o nome do Dependente: ");
                        d.setNome(scanner.nextLine());
                        System.out.println("A data de nascimento do Dependente (dd/MM/yyyy): ");
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        d.setDataNascimento(new java.sql.Date(format.parse(scanner.nextLine()).getTime()));
                        ORMHibernate.atualizarDependente(d);
                    } catch (ParseException ex) {
                        System.out.println(ex.toString());
                    }
                    break;
                }
                case 4: {
                    System.out.println("Digite o Id do Dependente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    ORMHibernate.excluirDependente(id);
                    break;
                }
            }
        } while (opcao != 5);
    }
}

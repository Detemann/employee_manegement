package sarrussys.main.views;

import sarrussys.main.database.ConexaoDB;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Menu {
    private Scanner sc;
    private ConexaoDB conexao;

    public Menu(){
        this.sc = new Scanner(System.in);
        this.conexao = new ConexaoDB();
    }

    public void inicializacao() throws IOException, SQLException {

        boolean sair = false;

        int quant_funcionario = 10;
        int quant_departamento = 10;

        try {
            //this.conexao.initDB();
            quant_funcionario = contarFuncionarios();
            quant_departamento = contarDepartamentos();
            System.out.println("Total de funcionários: " + quant_funcionario); //PARA TESTAR SE ESTA CORRETO O RETORNO
            System.out.println("Total de departamentos: " + quant_departamento); //PARA TESTAR SE ESTA CORRETO O RETORNO
            //this.conexao.fecharConexao(); //teste
        } catch (SQLException e) {
            e.printStackTrace();
        }


        do{

            System.out.println("######################################################");
            System.out.println("#         SISTEMA DE CONTROLE DE FUNCIONARIO         #");
            System.out.println("#                                                    #");
            System.out.println("#   TOTAL DE REGISTROS EXISTENTES                    #");
            System.out.println("#        1- FUNCIONARIOS: " + String.format("%-27d", quant_funcionario) + "#");
            System.out.println("#        2- DEPARTAMENTOS: " + String.format("%-26d", quant_departamento) + "#");
            System.out.println("#                                                    #");
            System.out.println("#                                                    #");
            System.out.println("#   CRIADO POR: LUCAS DETEMANN                       #");
            System.out.println("#               NATALIA TAYAR                        #");
            System.out.println("#               WANDERSON GONÇALVES                  #");
            System.out.println("#                                                    #");
            System.out.println("#                                                    #");
            System.out.println("#   DISCIPLINA: BANCO DE DADOS 2023/2                #");
            System.out.println("#   PROFESSOR: HOWARD ROATTI                         #");
            System.out.println("######################################################");

            System.out.println("Pressione ENTER para prosseguir");
            int input = System.in.read();

            if (input == 10) {
                sair = true;
            }
        }while (!sair);
        menuPrincipal();
    }

    public void menuPrincipal(){
        Integer op = null;

        do {
            System.out.println("#======================= MENU PRINCIPAL =======================#");
            System.out.println("#                                                              #"
                    +"\n#                       ESCOLHA UMA OPÇÃO                      #"
                    +"\n#                                                              #"
                    +"\n#   [1] Relatórios                                             #"
                    +"\n#   [2] Inserir Registros                                      #"
                    +"\n#   [3] Remover Registros                                      #"
                    +"\n#   [4] Atualizar Registros                                    #"
                    +"\n#   [5] Sair                                                   #"
                    +"\n#                                                              #"
            );
            System.out.println("#==============================================================#\n");
            op = sc.nextInt();

            switch (op){
                case 1:
                    //relatórios
                    System.out.println("Relatorios");
                    break;
                case 2:
                    //inserir registros
                    System.out.println("Inserir Registros");
                    break;
                case 3:
                    //remover registros
                    System.out.println("Remover registros");
                    break;
                case 4:
                    //atualizar registros
                    System.out.println("Atualizar Registros");
                    break;
                case 5:
                    System.out.println("\n\n\n FIM DO PROGRAMA!");
                    break;
                default:
                    System.out.println("Opção indisponível!\nTente Novamente");
                    break;
            }
        } while (op != 5);
    }

    public int contarFuncionarios() throws SQLException {
        int totalFuncionarios = 0;
        try {
            this.conexao.initDB(); //teste
            ResultSet consulta = conexao.executarConsulta("SELECT COUNT(1) total_funcionario FROM FUNCIONARIO");

            if (consulta.next()) {
                totalFuncionarios = consulta.getInt("total_funcionario");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.conexao.fecharConexao(); //teste
        return totalFuncionarios;
    }

    public int contarDepartamentos() throws SQLException {
        int totalDepartamentos = 0;
        try {//o ponto e virgula no final do script select count estava dando erro na consulta
            ResultSet consulta = conexao.executarConsulta("SELECT COUNT(1) total_departamento FROM DEPARTAMENTO");
            if (consulta.next()) {
                totalDepartamentos = consulta.getInt("total_departamento");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalDepartamentos;
    }


}

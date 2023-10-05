package sarrussys.main.services;

import oracle.jdbc.pool.OracleDataSource;
import sarrussys.main.model.Departamento;
import sarrussys.main.model.Funcionario;
import sarrussys.main.services.database.DatabaseServices;


import java.sql.ResultSet;
import java.sql.SQLException;;
import java.util.ArrayList;
import java.util.List;

public class RelatorioServices {
    private DatabaseServices servicosBanco;

    //construtor
    public RelatorioServices(OracleDataSource conexao){
        this.servicosBanco = new DatabaseServices(conexao);
    }

    //relatorio que retorna cada departamento e o numero de funcionarios respectavamente
    public List<String> relatorioDepartamentoNumFuncionarios(){
        List<String> resultado = new ArrayList<>();
        try {
            ResultSet consulta = this.servicosBanco.fazerConsulta("SELECT DEPARTAMENTO.NOME AS Nome_Departamento, COUNT(FUNCIONARIO.ID_FUNCIONARIO) AS Numero_Funcionarios\n" +
                    "FROM DEPARTAMENTO\n" +
                    "LEFT JOIN FUNCIONARIO ON DEPARTAMENTO.ID_DEPARTAMENTO = FUNCIONARIO.ID_DEPARTAMENTO\n" +
                    "GROUP BY DEPARTAMENTO.NOME");
            while(consulta.next()) {
                resultado.add(consulta.getString("NOME_DEPARTAMENTO"));
                resultado.add(consulta.getString("NUMERO_FUNCIONARIOS"));
            }
            if(!resultado.isEmpty()){ //se estiver cheia retorna a lista se não, retorna null
                return resultado;
            }else{
                //Nenhum registro encontrado!
                return null;
            }
        }catch (SQLException e) {
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
            return null;
        }
    }

    //relatorio que retorna os funcionarios e seu respectivo departamento
    public List<String> relatorioFuncionarioDepartamento(){
        List<String> resultado = new ArrayList<>();

        try {
            ResultSet consulta = this.servicosBanco.fazerConsulta("SELECT FUNCIONARIO.NOME AS Nome_Funcionario, DEPARTAMENTO.NOME AS Nome_Departamento\n" +
                    "FROM FUNCIONARIO\n" +
                    "LEFT JOIN DEPARTAMENTO ON FUNCIONARIO.ID_DEPARTAMENTO = DEPARTAMENTO.ID_DEPARTAMENTO");
            while(consulta.next()) {
                resultado.add(consulta.getString("NOME_FUNCIONARIO"));
                resultado.add(consulta.getString("NOME_DEPARTAMENTO"));
            }
            if(!resultado.isEmpty()){//se estiver cheia retorna a lista se não, retorna null
                return resultado;
            }else{
                //Nenhum registro encontrado!
                return null;
            }
        }catch (SQLException e) {
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
            return null;
        }
    }

    //relatorio que retorna os departamentos e o seu respectivo chefe
    public List<String> relatorioDepartamentoChefe(){
        List<String> resultado = new ArrayList<>();

        try {
            ResultSet consulta = this.servicosBanco.fazerConsulta("SELECT DEPARTAMENTO.NOME AS Nome_Departamento, FUNCIONARIO.NOME AS Nome_Chefe\n" +
                    "FROM DEPARTAMENTO\n" +
                    "LEFT JOIN FUNCIONARIO ON DEPARTAMENTO.ID_CHEFE = FUNCIONARIO.ID_FUNCIONARIO");
            while(consulta.next()) {
                resultado.add(consulta.getString("NOME_DEPARTAMENTO"));
                resultado.add(consulta.getString("NOME_CHEFE"));
            }
            if(!resultado.isEmpty()){//se estiver cheia retorna a lista se não, retorna null
                return resultado;
            }else{
                //Nenhum registro encontrado!
                return null;
            }
        }catch (SQLException e) {
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
            return null;
        }
    }

    //metodo que faz consulta sql e retorna a quantidade de departamentos cadastrados no banco
    public int contarDepartamentosService(){
        int totalDepartamentos = 0;

        try {//o ponto e virgula no final do script select count estava dando erro na consulta
            ResultSet consulta = this.servicosBanco.fazerConsulta("SELECT COUNT(1) total_departamento FROM DEPARTAMENTO");
            if (consulta.next()) {
                totalDepartamentos = consulta.getInt("total_departamento");
            }
        } catch (SQLException e) {
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
        }
        return totalDepartamentos;
    }

    //metodo que faz consulta sql e retorna a quantidade de funcionarios cadastrados no banco
    public int contarFuncionariosServices(){
        int totalFuncionarios = 0;
        try {
            ResultSet consulta = this.servicosBanco.fazerConsulta("SELECT COUNT(1) total_funcionario FROM FUNCIONARIO");
            if (consulta.next()) {
                totalFuncionarios = consulta.getInt("total_funcionario");
            }
        } catch (SQLException e) {
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
        }
        return totalFuncionarios;
    }

    public List<Departamento> mostraDepartamentos(){
        List<Departamento> resultado = new ArrayList<>();
        Departamento departamento;

        try {
            String sql = "SELECT * FROM DEPARTAMENTO";
            ResultSet consulta = this.servicosBanco.fazerConsulta(sql);
            while (consulta.next()){
                int id = consulta.getInt("ID_DEPARTAMENTO");
                String nome = consulta.getString("NOME");
                String sigla = consulta.getString("SIGLA");
                Integer idChfe = consulta.getInt("ID_CHEFE");

                if(idChfe != null){
                    Funcionario chefe = pesquisaFuncionarioID(idChfe);
                    departamento = new Departamento(id, nome, sigla, chefe);
                    resultado.add(departamento);
                }else{
                    Funcionario chefe = new Funcionario(null,null,null,null,null,null);
                    departamento = new Departamento(id, nome, sigla, chefe);
                    resultado.add(departamento);
                }
            }
            if(!resultado.isEmpty()){//se estiver cheia retorna a lista se não, retorna null
                return resultado;
            }else{
                //Nenhum registro encontrado!
                return null;
            }
        }catch (SQLException e){
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
            return null;
        }
    }

    public List<Funcionario> mostrarFuncionarios(){
        List<Funcionario> resultado = new ArrayList<>();

        try {
            String sql = "SELECT * FROM FUNCIONARIO";
            ResultSet consulta = this.servicosBanco.fazerConsulta(sql);
            Funcionario funcionario;

            while (consulta.next()){
                Integer id = consulta.getInt("ID_FUNCIONARIO");
                String nome = consulta.getString("NOME");
                String cpf = consulta.getString("CPF");
                String email = consulta.getString("EMAIL");
                double salarioBruto = consulta.getDouble("SALARIO_BRUTO");
                double salarioLiquido = consulta.getDouble("SALARIO_LIQUIDO");
                Integer idDepartamento = consulta.getInt("ID_DEPARTAMENTO");

                Departamento departamento = pesquisaDepartamentoID(idDepartamento);
                funcionario = new Funcionario(id, nome, cpf, email, salarioBruto, salarioLiquido, departamento);
                resultado.add(funcionario);
            }

            if(!resultado.isEmpty()){//se estiver cheia retorna a lista se não, retorna null
                return resultado;
            }else{
                //Nenhum registro encontrado!
                return null;
            }
        }catch (SQLException e){
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
            return null;
        }
    }


    public Funcionario pesquisaFuncionarioID(Integer id){
        Funcionario funcionario = new Funcionario();

        try{
            String sql = "SELECT *\n" +
                    "FROM FUNCIONARIO\n" +
                    "WHERE ID_FUNCIONARIO = "+id;
            ResultSet consulta = this.servicosBanco.fazerConsulta(sql);

            if (consulta.next()){
                String nome = consulta.getString("NOME");
                String cpf = consulta.getString("CPF");
                String email = consulta.getString("EMAIL");
                double salarioBruto = consulta.getDouble("SALARIO_BRUTO");
                double salarioLiquido = consulta.getDouble("SALARIO_LIQUIDO");
                Integer idDepartamento = consulta.getInt("ID_DEPARTAMENTO");
                funcionario.setIdFuncionario(id);
                funcionario.setNome(nome);
                funcionario.setCpf(cpf);
                funcionario.setEmail(email);
                funcionario.setSalarioBruto(salarioBruto);
                funcionario.setSalarioLiquido(salarioLiquido);
                return funcionario;
            }

        }catch (SQLException e){
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
        }
        return null;
    }

    public Departamento pesquisaDepartamentoID(Integer id){
        Departamento departamento = new Departamento();

        try{
        String sql = "SELECT *\n" +
                "FROM DEPARTAMENTO\n" +
                "WHERE ID_DEPARTAMENTO = "+id;
            ResultSet consulta = this.servicosBanco.fazerConsulta(sql);

            if (consulta.next()){
                Integer idDepartamento = consulta.getInt("ID_DEPARTAMENTO");
                String nome = consulta.getString("NOME");
                String sigla = consulta.getString("SIGLA");
                Integer idChfe = consulta.getInt("ID_CHEFE");
                departamento.setIdDepartamento(idDepartamento);
                departamento.setNomeDepartamento(nome);
                departamento.setSigla(sigla);
                Funcionario chefeDepartamento = pesquisaFuncionarioID(idChfe);
                departamento.setChefeDepartamento(chefeDepartamento);
                return departamento;
            }

        }catch (SQLException e){
            System.out.println("[MenuService] Ocorreu um erro inesperado: /n"+e.getMessage());
        }
        return null;
    }
}

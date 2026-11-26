package com.locacaoquadras.config;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import com.locacaoquadras.dao.AdministradorDAO;
import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Administrador;
import com.locacaoquadras.model.Horario;
import com.locacaoquadras.model.Modalidade;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.model.Usuario;

// Cria os dados mínimos para o sistema ser usado logo na primeira execução.
// Só insere quando a tabela está VAZIA, então pode rodar a cada inicialização sem duplicar nada.
public final class DadosIniciais {

    // >>> TROQUE A SENHA DO ADMINISTRADOR PADRÃO no primeiro acesso (menu Administradores).
    public static final String ADMIN_EMAIL = "admin@quadradas.com";
    public static final String ADMIN_SENHA = "admin123";

    // Quadras, horários e um cliente de teste. Ponha false para começar com o banco limpo.
    private static final boolean CARREGAR_EXEMPLOS = true;

    private DadosIniciais() {
    }

    public static void carregar() {
        try {
            criarAdministrador();
            if (CARREGAR_EXEMPLOS) {
                criarExemplos();
            }
        } catch (SQLException | RuntimeException e) {
            System.out.println("[QuadraDas] Não foi possível criar os dados iniciais: " + e.getMessage());
        }
    }

    private static void criarAdministrador() throws SQLException {
        AdministradorDAO dao = new AdministradorDAO();
        if (dao.contarAdministradores() == 0) {
            Administrador a = new Administrador();
            a.setNome("Administrador");
            a.setEmail(ADMIN_EMAIL);
            a.setSenha(ADMIN_SENHA);
            dao.createAdministrador(a);
            System.out.println("[QuadraDas] Administrador criado: " + ADMIN_EMAIL + " / " + ADMIN_SENHA);
        }
    }

    private static void criarExemplos() throws SQLException {
        ModalidadeDAO modDao = new ModalidadeDAO();
        if (modDao.findAllModalidades().isEmpty()) {
            modalidade(modDao, "Futebol", "120.00", "150.00");
            modalidade(modDao, "Futsal", "90.00", "110.00");
            modalidade(modDao, "FutVôlei", "70.00", "90.00");
        }

        QuadraDAO quadraDao = new QuadraDAO();
        if (quadraDao.findAllQuadras().isEmpty()) {
            List<Modalidade> mods = modDao.findAllModalidades();
            int futebol = id(mods, "Futebol");
            int futsal = id(mods, "Futsal");
            int futvolei = id(mods, "FutVôlei");

            String estrutura = "Grama sintética, Iluminação LED, Vestiário, Estacionamento";
            quadra(quadraDao, "Quadra do Centro", "Centro, São Paulo - SP", estrutura, futebol);
            quadra(quadraDao, "Arena Sport", "Pinheiros, São Paulo - SP", "Piso de alta performance, Iluminação LED, Vestiário", futsal);
            quadra(quadraDao, "PlayQuadra", "Vila Mariana, São Paulo - SP", "Areia fina, Iluminação LED, Churrasqueira", futvolei);
            quadra(quadraDao, "Esporte Clube", "Moema, São Paulo - SP", estrutura, futebol);
            quadra(quadraDao, "Arena Norte", "Santana, São Paulo - SP", "Piso de alta performance, Vestiário, Estacionamento", futsal);
            quadra(quadraDao, "Sport Center", "Tatuapé, São Paulo - SP", estrutura, futebol);

            HorarioDAO horDao = new HorarioDAO();
            for (Quadra q : quadraDao.findAllQuadras()) {
                for (int hora = 18; hora <= 21; hora++) {
                    Horario h = new Horario();
                    h.setIdQuadra(q.getIdQuadra());
                    h.setHoraInicio(LocalTime.of(hora, 0));
                    h.setHoraFim(LocalTime.of(hora + 1, 0));
                    horDao.createHorario(h);
                }
            }
        }

        UsuarioDAO userDao = new UsuarioDAO();
        if (userDao.contarUsuarios() == 0) {
            Usuario u = new Usuario();
            u.setNome("Pedro Faro");
            u.setEmail("pedro@email.com");
            u.setTelefone("(11) 98765-4321");
            u.setSenha("123456");
            userDao.createUsuario(u);
            System.out.println("[QuadraDas] Cliente de teste criado: pedro@email.com / 123456");
        }
    }

    private static void modalidade(ModalidadeDAO dao, String nome, String semana, String fim) throws SQLException {
        Modalidade m = new Modalidade();
        m.setNome(nome);
        m.setPrecoSemana(new BigDecimal(semana));
        m.setPrecoFimdesemana(new BigDecimal(fim));
        dao.createModalidade(m);
    }

    private static void quadra(QuadraDAO dao, String nome, String endereco, String descricao, int idModalidade)
            throws SQLException {
        Quadra q = new Quadra();
        q.setNome(nome);
        q.setEndereco(endereco);
        q.setDescricao(descricao);
        q.setIdModalidade(idModalidade);
        q.setAtiva(true);
        dao.createQuadra(q);
    }

    private static int id(List<Modalidade> lista, String nome) {
        for (Modalidade m : lista) {
            if (m.getNome().equals(nome)) {
                return m.getIdModalidade();
            }
        }
        throw new IllegalStateException("Modalidade não encontrada: " + nome);
    }
}

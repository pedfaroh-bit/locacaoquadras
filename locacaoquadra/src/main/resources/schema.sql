CREATE TABLE IF NOT EXISTS modalidade (
  id_modalidade INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  preco_semana DECIMAL(10,2) NOT NULL,
  preco_fimdesemana DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  telefone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS administrador (
  id_administrador INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS quadra (
  id_quadra INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  endereco VARCHAR(255) NOT NULL,
  descricao VARCHAR(255),
  id_modalidade INT NOT NULL,
  FOREIGN KEY (id_modalidade) REFERENCES modalidade(id_modalidade)
);

CREATE TABLE IF NOT EXISTS horario (
  id_horario INT AUTO_INCREMENT PRIMARY KEY,
  hora_inicio TIME NOT NULL,
  hora_fim TIME NOT NULL,
  id_quadra INT NOT NULL,
  FOREIGN KEY (id_quadra) REFERENCES quadra(id_quadra)
);

CREATE TABLE IF NOT EXISTS reserva (
  id_reserva INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  id_horario INT NOT NULL,
  data_reserva DATE NOT NULL,
  status ENUM('Ativa','Cancelada') NOT NULL DEFAULT 'Ativa',
  valor_pago DECIMAL(10,2) NOT NULL,
  valor_devolvido DECIMAL(10,2) NULL,
  data_cancelamento TIMESTAMP NULL,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  FOREIGN KEY (id_horario) REFERENCES horario(id_horario)
);

CREATE TABLE IF NOT EXISTS bloqueio (
  id_bloqueio INT AUTO_INCREMENT PRIMARY KEY,
  id_administrador INT NOT NULL,
  id_horario INT NOT NULL,
  data_bloqueio DATE NOT NULL,
  motivo VARCHAR(255),
  FOREIGN KEY (id_administrador) REFERENCES administrador(id_administrador),
  FOREIGN KEY (id_horario) REFERENCES horario(id_horario)
);

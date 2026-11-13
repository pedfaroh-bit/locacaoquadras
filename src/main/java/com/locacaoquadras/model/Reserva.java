package com.locacaoquadras.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reserva {

    private int idReserva;
    private int idUsuario;
    private int idHorario;
    private LocalDate dataReserva;
    private String status;
    private BigDecimal valorPago;
    private BigDecimal valorDevolvido;
    private LocalDateTime dataCancelamento;

    // Dados das tabelas ligadas, só para exibir na tela
    private String nomeUsuario;
    private String nomeQuadra;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getValorDevolvido() {
        return valorDevolvido;
    }

    public void setValorDevolvido(BigDecimal valorDevolvido) {
        this.valorDevolvido = valorDevolvido;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeQuadra() {
        return nomeQuadra;
    }

    public void setNomeQuadra(String nomeQuadra) {
        this.nomeQuadra = nomeQuadra;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getDataReservaFormatada() {
        return dataReserva == null ? "" : dataReserva.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getDataCancelamentoFormatada() {
        return dataCancelamento == null ? "" : dataCancelamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}

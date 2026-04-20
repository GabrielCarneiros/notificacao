package com.carneiro.notificacao.bussiness.dto;

import com.carneiro.notificacao.infrastructure.enums.StatusNotificacaoEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefasDTO {
    private String id;
    private String nomeTarefa;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEvento;
    private String emalUsuario;
    private LocalDateTime dataAlteracao;
    private StatusNotificacaoEnum status;

}

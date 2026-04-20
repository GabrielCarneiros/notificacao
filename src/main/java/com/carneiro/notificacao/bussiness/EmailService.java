package com.carneiro.notificacao.bussiness;

import com.carneiro.notificacao.bussiness.dto.TarefasDTO;
import com.carneiro.notificacao.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio remetente}")
    public String remetente;

    @Value("${envio.email.nomeRemetente")
    private String nomeRemetente;

    public void enviaEmail(TarefasDTO dto){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMessageHelper.setTo(InternetAddress.parse(dto.getEmalUsuario()));
            mimeMessageHelper.setSubject("Notificação de tarefa");

            Context context = new Context();
            context.setVariable("nomeTarefa", dto.getNomeTarefa());
            context.setVariable("dataEvento", dto.getDataEvento());
            context.setVariable("descricao", dto.getDescricao());
            String template = templateEngine.process("notificacao.html", context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(message);

        } catch (UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar o email", e.getCause());
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

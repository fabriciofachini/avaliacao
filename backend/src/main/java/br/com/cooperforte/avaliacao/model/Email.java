package br.com.cooperforte.avaliacao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
@Table(name = "TB_EMAIL")
public class Email implements Serializable {

	private static final long serialVersionUID = 4316968318562811208L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_EMAIL_ID_EMAIL_SEQ")
	@SequenceGenerator(name = "TB_EMAIL_ID_EMAIL_SEQ", sequenceName = "SEQ_EMAIL", allocationSize = 1)
	@Column(name = "ID_EMAIL", updatable = false)
	private Long id;

	@Column(name = "DS_EMAIL", nullable = false)
	@NotBlank(message = "O e-mail é de preenchimento obrigatório")
	@javax.validation.constraints.Email(message = "O e-mail informado é inválido")
	private String email;

	@ManyToOne
	@JoinColumn(name = "ID_CLIENTE", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private Usuario usuario;

	@Column(name = "DH_ALTERACAO", nullable = false)
	private LocalDateTime dataHora;

}

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
import javax.validation.constraints.NotNull;

import br.com.cooperforte.avaliacao.enums.TipoTelefone;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_TELEFONE")
public class Telefone implements Serializable {

	private static final long serialVersionUID = -5240628935025613347L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_TELEFONE_ID_TELEFONE_SEQ")
	@SequenceGenerator(name = "TB_TELEFONE_ID_TELEFONE_SEQ", sequenceName = "SEQ_TELEFONE", allocationSize = 1)
	@Column(name = "ID_TELEFONE", updatable = false)
	private Long id;

	@Column(name = "NU_TELEFONE", nullable = false)
	@NotBlank(message = "O número do telefone é de preenchimento obrigatório")
	private String telefone;

	@Column(name = "ID_TIPO_TELEFONE", nullable = false)
	@NotNull(message = "O e-mail é de preenchimento obrigatório")
	private TipoTelefone tipo;

	@ManyToOne
	@JoinColumn(name = "ID_CLIENTE", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private Usuario usuario;

	@Column(name = "DH_ALTERACAO", nullable = false)
	private LocalDateTime dataHora;

}

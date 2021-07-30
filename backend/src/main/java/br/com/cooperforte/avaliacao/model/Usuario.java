package br.com.cooperforte.avaliacao.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.cooperforte.avaliacao.enums.Perfil;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 5881255601710665039L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_USUARIO_ID_USUARIO_SEQ")
	@SequenceGenerator(name = "TB_USUARIO_ID_USUARIO_SEQ", sequenceName = "SEQ_USUARIO", allocationSize = 1)
	@Column(name = "ID_USUARIO", updatable = false)
	private Long id;

	@Column(name = "DS_LOGIN")
	@NotBlank(message = "O login é de preenchimento obrigatório")
	private String login;

	@Column(name = "DS_SENHA")
	@Basic(fetch = FetchType.LAZY)
	private String senha;

	@Column(name = "NO_USUARIO")
	@NotBlank(message = "O nome é de preenchimento obrigatório")
	private String nome;

	@Column(name = "ID_PERFIL")
	@NotNull(message = "O perfil é de preenchimento obrigatório")
	private Perfil perfil;

}

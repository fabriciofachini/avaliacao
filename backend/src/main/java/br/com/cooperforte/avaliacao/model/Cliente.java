package br.com.cooperforte.avaliacao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Entity
@Data
@Table(name = "TB_CLIENTE")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 4223790311818790213L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_CLIENTE_ID_CLIENTE_SEQ")
	@SequenceGenerator(name = "TB_CLIENTE_ID_CLIENTE_SEQ", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
	@Column(name = "ID_CLIENTE", updatable = false)
	private Long id;

	@Column(name = "NO_CLIENTE", nullable = false)
	@NotBlank(message = "O nome é de preenchimento obrigatório")
	@Size(min = 3, max = 100, message = "O nome deve conter de 3 até 100 caracteres.")
	private String nome;

	@Column(name = "NU_CPF", nullable = false)
	@NotBlank(message = "O CPF é de preenchimento obrigatório")
	@CPF
	private String cpf;

	@Column(name = "NU_CEP", nullable = false)
	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String cep;

	@Column(name = "DS_LOGRADOURO", nullable = false)
	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String logradouro;

	@Column(name = "DS_COMPLEMENTO")
	private String complemento;

	@Column(name = "DS_BAIRRO", nullable = false)
	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String bairro;

	@Column(name = "NO_CIDADE", nullable = false)
	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String cidade;

	@Column(name = "SG_UF", nullable = false)
	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String uf;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<Telefone> telefones;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<Email> emails;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private Usuario usuario;

	@Column(name = "DH_ALTERACAO", nullable = false)
	private LocalDateTime dataHora;

}

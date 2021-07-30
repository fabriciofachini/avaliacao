import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { Cliente } from '../../../interfaces/cliente';
import { Cep } from '../../../interfaces/cep';
import { useFormik, FormikProvider } from 'formik';
import * as Yup from 'yup';
import { Button, createStyles, TextField, TextFieldProps, Theme, Backdrop, CircularProgress } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import InputMask, {Props} from 'react-input-mask';
import api from '../../../services/api';
import './loading.scss';
import { catchError } from 'rxjs/operators';
import { Email } from '../../../interfaces/email';
import { throwError } from 'rxjs';
import { ModalEmail } from './modals/modal-email';
import { FaTrash } from 'react-icons/fa';
import { Telefone } from '../../../interfaces/telefone';
import { ModalTelefone } from './modals/modal-telefone';

const CriarClientePage: React.FC = () => {
  let { id }: any = useParams();
  const [cliente, setCliente] = useState<Cliente>({} as Cliente);
  const [loading, setLoading] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [isOpenTelefone, setIsOpenTelefone] = useState(false);
  const [mailList, setMailList] = useState<Array<Email>>([]);
  const [telefoneList, setTelefoneList] = useState<Array<Telefone>>([]);
  const history = useHistory();

  const [x, setX] = useState('ola');

  const formik = useFormik({
    initialValues: cliente,
    validationSchema: Yup.object({
      id: Yup.number(),
      nome: Yup.string()
        .max(100, 'Nome deve conter até de 100 caracteres')
        .min(3, 'Nome deve conter pelo menos 3 caracteres')
        // .matches(/^[\w\s]*$/, 'Nome deve conter apenas caracteres alfanuméricos')
        .required('Campo obrigatório'),
      cpf: Yup.string().required('Nome é obrigatório'),
      cep: Yup.string().required('CEP é obrigatório'),
      logradouro: Yup.string().required('Logradouro é obrigatório'),
      bairro: Yup.string().required('Bairro é obrigatório'),
      cidade: Yup.string().required('Cidade é obrigatória'),
      uf: Yup.string().required('UF é obrigatório'),
      complemento: Yup.string()
    }),
    onSubmit: (values: Cliente) => {
      if (mailList.length === 0 || telefoneList.length === 0) {
        return;
      }
      setLoading(true);

      const cliente: any = {
        nome: values.nome,
        cpf: values.cpf,
        cep: values.cep.replace(/\D/g, ''),
        bairro: values.bairro,
        logradouro: values.logradouro,
        cidade: values.cidade,
        uf: values.uf,
        complemento: values.complemento,
        telefones: telefoneList,
        emails: mailList
      };

      let obs = api.post<Cliente>('/v1/cliente', cliente);
      if (values.id) {
        obs = api.put<Cliente>('/v1/cliente/' + values.id, cliente);
      }
      obs.subscribe(() => {
            setLoading(false);
            history.push('/');
        }, catchError(error => {
            setLoading(false);
            return throwError(error);
        })
      );
    },
  });

  useEffect(() => {
    if (id) {
      api.get<Cliente>('/v1/cliente/' + id).subscribe((response) => {
        if (response) {
          setCliente(response);
          formik.setValues(response);
          setMailList(response.emails || []);
          setTelefoneList(response.telefones || []);
        }
      });
    }
  },[]);

  const onChangeCep = (event: any) => {
    const cep = event.target.value.replace(/\D/g, '');
    if (cep.length === 8) {
      setLoading(true);
      api.get<Cep>('/v1/cep/' + cep).subscribe((data) => {
        if (data && Object.keys(data).length > 0) {
          formik.setFieldValue('bairro', data.bairro, true);
          formik.setFieldValue('logradouro', data.logradouro, true);
          formik.setFieldValue('complemento', data.complemento, true);
          formik.setFieldValue('cidade', data.localidade, true);
          formik.setFieldValue('uf', data.uf, true);
        }
        setLoading(false);
      })
    }
  };

  const useStyles = makeStyles((theme: Theme) =>
    createStyles({
      backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
      },
      info: {
        padding: '5px 10px',
        border: '1px solid blue',
        borderRadius: '5px',
        marginBottom: '5px',
        maxWidth: 300
      }
    }),
  );

  const classes: any = useStyles();

  const removerEmail = (id: number | undefined, index: number) => {
    mailList.splice(index, 1);
    if (id) {
      api.delete('/v1/cliente/email', id).subscribe();
      setMailList(mailList);
    }
  };

  const removerTelefone = (id: number | undefined, index: number) => {
    telefoneList.splice(index, 1);
    if (id) {
      api.delete('/v1/cliente/telefone', id).subscribe();
      console.log(telefoneList);
      setTelefoneList(telefoneList);
    }
  };

  return (
    <div style={{padding: 50}}>
        {x}
      <FormikProvider value={formik}>
        <form onSubmit={formik.handleSubmit}>
          <TextField
            style={{margin: 10}}
            fullWidth
            id="nome"
            name="nome"
            label="Nome"
            value={formik.values.nome}
            onChange={formik.handleChange}
            error={formik.touched.nome && Boolean(formik.errors.nome)}
            helperText={formik.touched.nome && formik.errors.nome}
          />

          <InputMask
            mask="999.999.999-99"
            value={formik.values.cpf}
            onChange={formik.handleChange}
          >
            {(inputProps: Props & TextFieldProps)=>
              <TextField
                {...inputProps}
                type="tel"
                style={{margin: 10}}
                label="CPF"
                fullWidth
                id="cpf"
                name="cpf"
                error={formik.touched.cpf && Boolean(formik.errors.cpf)}
                helperText={formik.touched.cpf && formik.errors.cpf}
              />
            }
          </InputMask>
          <InputMask
            mask="99999-999"
            value={formik.values?.cep}
            onChange={formik.handleChange}
            onBlur={onChangeCep}
          >
            {(inputProps: Props & TextFieldProps)=>
              <TextField
                {...inputProps}
                type="tel"
                label="CEP"
                style={{margin: 10}}
                fullWidth
                id="cep"
                name="cep"
                error={formik.touched.cep && Boolean(formik.errors.cep)}
                helperText={formik.touched.cep && formik.errors.cep}
              />
            }
          </InputMask>
          <TextField
            fullWidth
            InputLabelProps={{
              shrink: true,
            }}
            id="logradouro"
            name="logradouro"
            style={{margin: 10}}
            label="Logradouro"
            value={formik.values.logradouro}
            onChange={formik.handleChange}
            error={formik.touched.logradouro && Boolean(formik.errors.logradouro)}
            helperText={formik.touched.logradouro && formik.errors.logradouro}
          />
          <TextField
            fullWidth
            InputLabelProps={{
              shrink: true,
            }}
            style={{margin: 10}}
            id="bairro"
            name="bairro"
            label="Bairro"
            value={formik.values.bairro}
            onChange={formik.handleChange}
            error={formik.touched.bairro && Boolean(formik.errors.bairro)}
            helperText={formik.touched.bairro && formik.errors.bairro}
          />
          <TextField
            fullWidth
            InputLabelProps={{
              shrink: true,
            }}
            style={{margin: 10}}
            id="cidade"
            name="cidade"
            label="Cidade"
            value={formik.values.cidade}
            onChange={formik.handleChange}
            error={formik.touched.cidade && Boolean(formik.errors.cidade)}
            helperText={formik.touched.cidade && formik.errors.cidade}
          />
          <TextField
            fullWidth
            InputLabelProps={{
              shrink: true,
            }}
            style={{margin: 10}}
            id="uf"
            name="uf"
            label="UF"
            value={formik.values.uf}
            onChange={formik.handleChange}
            error={formik.touched.uf && Boolean(formik.errors.uf)}
            helperText={formik.touched.uf && formik.errors.uf}
          />
          <TextField
            fullWidth
            style={{margin: 10}}
            InputLabelProps={{
              shrink: true,
            }}
            id="complemento"
            name="complemento"
            label="Complemento"
            value={formik.values.complemento}
            onChange={formik.handleChange}
            error={formik.touched.complemento && Boolean(formik.errors.complemento)}
            helperText={formik.touched.complemento && formik.errors.complemento}
          />
          {
            mailList.map((email, index) => {
              return <div className={classes.info} style={{position: 'relative'}} key={index}>
                {email.email}
                <Button type="button" style={{position: 'absolute', right: '-10px'}}
                        onClick={() => removerEmail(email.id, index)}><FaTrash/></Button>
              </div>
            })
          }
          <Button color="primary" type="button" variant="contained" onClick={() => {
              setX('asdasd');
            setIsOpen(true);
          }} style={{margin: 10}}>
            Adicionar email
          </Button>

          <ModalEmail isOpen={isOpen} confirm={(value: string) => {
            setIsOpen(false);
            mailList.push({
              email: value
            });
            setMailList(mailList);
          }}/>

          {
            telefoneList.map((telefone, index) => {
              return <div className={classes.info} style={{position: 'relative'}} key={index}>
                {telefone.telefone} ({telefone.tipo === 'RE' ? 'Residencial' : telefone.tipo === 'CO' ? 'Comercial' : 'Celular'})
                <Button type="button" style={{position: 'absolute', right: '-10px'}}
                        onClick={() => removerTelefone(telefone.id, index)}><FaTrash/></Button>
              </div>
            })
          }
          <Button color="primary" type="button" variant="contained" onClick={() => {
            setIsOpenTelefone(true);
          }} style={{margin: 10}}>
            Adicionar telefone
          </Button>

          <ModalTelefone isOpen={isOpenTelefone} confirm={(value: Telefone) => {
            setIsOpenTelefone(false);
            telefoneList.push(value);
            setTelefoneList(telefoneList);
          }}/>

          <Button color="primary" variant="contained" fullWidth type="submit">
            Cadastrar
          </Button>
        </form>
      </FormikProvider>
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
    </div>
  );
};

export default CriarClientePage;

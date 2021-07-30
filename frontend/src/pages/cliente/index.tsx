import React, { useEffect, useState } from 'react';
import { FaEdit, FaWindowClose, FaSignOutAlt } from 'react-icons/fa';
import { useHistory } from 'react-router-dom';
import api from '../../services/api';
import { Cliente } from '../../interfaces/cliente';
import { withStyles, Theme, createStyles, makeStyles } from '@material-ui/core/styles';
import { Button, IconButton, Paper, Table, TableCell, TableContainer, TableRow, TableHead, TableBody} from '@material-ui/core';
import { useAuth } from '../../context/auth';

const ClientePage: React.FC = () => {

  const { admin, signOut } = useAuth();
  const [ loading, setLoading ] = useState(false);

  const StyledTableCell = withStyles((theme: Theme) =>
    createStyles({
      head: {
        backgroundColor: theme.palette.primary.light,
        color: theme.palette.common.white,
      },
      body: {
        fontSize: 14,
      },
    }),
  )(TableCell);

  const StyledTableRow = withStyles((theme: Theme) =>
    createStyles({
      root: {
        '&:nth-of-type(odd)': {
          backgroundColor: theme.palette.action.hover,
        },
      },
    }),
  )(TableRow);

  const useStyles = makeStyles({
    table: {
      minWidth: 700,
    },
  });

  const history = useHistory();

  const [clients, setClients] = useState<Cliente[]>([]);

  useEffect(() => {
    api.get<Cliente[]>('/cliente').subscribe((response) => {
      setClients(response);
    });
  },[loading]);

  const edit = (client: Cliente) => {
    history.push('/cliente/' + client.idCliente);
  };

  const remove = (client: Cliente) => {
    setLoading(true);
    api.delete<Cliente>('/cliente', client.idCliente).subscribe((response) => {
      setLoading(false);
    });
  };

  const create = () => {
    history.push('/cliente');
  };

  const classes = useStyles();

  return (
    <div>
      <div style={{margin: 10, display: 'flex', justifyContent: 'flex-end'}}>
        <IconButton color="primary" aria-label="add to shopping cart" onClick={() => {
          signOut();
          history.replace('/login');
        }}>
          <FaSignOutAlt />
        </IconButton>
      </div>
      <div style={{margin: 30, display: 'flex', justifyContent: 'center'}}>
        {admin && <Button variant="contained" color="primary" onClick={() => create()}>
            Cadastrar novo cliente
        </Button>}
      </div>
      <TableContainer component={Paper}>
          <Table className={classes.table} aria-label="customized table">
              <TableHead>
                  <TableRow>
                      <StyledTableCell>#</StyledTableCell>
                      <StyledTableCell align="right">Nome</StyledTableCell>
                      <StyledTableCell align="right">CPF</StyledTableCell>
                      <StyledTableCell align="right">Endereço</StyledTableCell>
                      <StyledTableCell align="right">E-mail(s)</StyledTableCell>
                      <StyledTableCell align="right">Telefone(s)</StyledTableCell>
                      <StyledTableCell align="right">&nbsp;</StyledTableCell>
                  </TableRow>
              </TableHead>
              <TableBody>
                {clients.map((client) => {
                  return <StyledTableRow key={client.idCliente}>
                    <StyledTableCell component="th" scope="row">
                      {client.idCliente}
                    </StyledTableCell>
                    <StyledTableCell align="right">{client.nome}</StyledTableCell>
                    <StyledTableCell align="right">{client.cpf}</StyledTableCell>
                    <StyledTableCell align="right">
                      <div><b>CEP:</b> {client.cep}</div>
                      <div><b>Logradouro:</b> {client.logradouro}</div>
                      <div><b>Bairro:</b> {client.bairro}</div>
                      <div><b>Cidade:</b> {client.cidade}</div>
                      <div><b>UF:</b> {client.uf}</div>
                      <div><b>Complemento:</b> {client.complemento}</div>
                    </StyledTableCell>
                    <StyledTableCell align="right">
                      {client.emails.map((value) => {
                        return <div key={value.id}>{value.dsEmail}</div>
                      })}
                    </StyledTableCell>
                    <StyledTableCell align="right">
                      {client.telefones.map((value) => {
                        return <div key={value.id}>{value.numero}</div>
                      })}
                    </StyledTableCell>
                    <StyledTableCell align="right">
                      <Button onClick={() => edit(client)}>
                        <FaEdit/>
                      </Button>
                      <Button style={{margin: 10}} onClick={() => remove(client)}>
                        <FaWindowClose/>
                      </Button>
                    </StyledTableCell>
                  </StyledTableRow>
                })}
              </TableBody>
          </Table>
      </TableContainer>
    </div>
  )
};


export default ClientePage;

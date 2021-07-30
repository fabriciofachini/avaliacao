import React, { useState } from 'react';
import './style.scss';
import { useAuth } from '../../context/auth';
import { Redirect } from 'react-router';
import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';
import { Button, Card, CardContent, Backdrop, IconButton, CircularProgress, Snackbar } from '@material-ui/core';
import { Form } from 'react-bootstrap';

const LoginPage: React.FC = () => {
  const { signed, signIn } = useAuth();

  const [login, setLogin] = useState<string>();
  const [senha, setSenha] = useState<string>();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  const handleSubmit = (event: any) => {
    const form = event.currentTarget;
    if (!login || ! senha) {
      event.preventDefault();
      event.stopPropagation();
      return;
    }
    setLoading(true);
    signIn(login, senha).subscribe(() => {
      setLoading(false);
    }, () =>{
      setError(true);
      setLoading(false);
      setTimeout(() => {
        setError(false);
      }, 1000);
    });
    event.preventDefault();
    event.stopPropagation();
  };

  const useStyles = makeStyles((theme: Theme) => {
      return createStyles({
        backdrop: {
          zIndex: theme.zIndex.drawer + 1,
          color: '#fff'
        },
        root: {
          minWidth: 320,
          position: 'absolute',
          left: '50%',
          top: '50%',
          transform: 'translate(-50%, -50%)'
        },
        bullet: {
          display: 'inline-block',
          margin: '0 2px',
          transform: 'scale(0.8)'
        },
        title: {
          fontSize: 14
        },
        pos: {
          marginBottom: 12
        }
      });
    },
  );

  const classes = useStyles();

  return (
    signed ? <Redirect to='/' /> : <div><Card className={classes.root}>
        <CardContent>
          <Form className="form" onSubmit={handleSubmit}>
            <Form.Group controlId="login">
              <Form.Label>Login</Form.Label>
              <Form.Control type="text" required placeholder="Usuário" onChange={(event: any) => {setLogin(event.target.value)}}/>
            </Form.Group>

            <Form.Group controlId="senha">
              <Form.Label>Senha</Form.Label>
              <Form.Control type="password" required placeholder="Senha" onChange={(event: any) => {setSenha(event.target.value)}} />
            </Form.Group>
            <Button variant="outlined" color="primary" type="submit">
              Entrar
            </Button>
          </Form>
          <div style={{height: 90, marginTop: 20}}><Snackbar
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'center',
            }}
            open={error}
            autoHideDuration={3000}
            message="Usuário e/ou senha inválidos."
          />
          </div>
        </CardContent>
      </Card>
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
    </div>
  );
};

export default LoginPage;

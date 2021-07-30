import React, { useEffect, useState } from 'react';
import { createStyles, makeStyles, Theme, Button, Modal, TextField, TextFieldProps } from '@material-ui/core';
import PropTypes from 'prop-types';

export const ModalEmail = (props: any) => {

  const [value, setValue] = useState('');

  const getModalStyle = () =>{
    return {
      top: '50%',
      left: '50%',
      transform: `translate(-50%, -50%)`,
    };
  };

  const useStyles = makeStyles((theme: Theme) =>
    createStyles({
      paper: {
        position: 'absolute',
        width: 400,
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
      },
      erro: {
        position: 'absolute',
        bottom: '62px',
        'font-size': '12px',
        color: 'red',
      },
      info: {
        padding: '5px 10px',
        border: '1px solid black',
        borderRadius: '10px',
        marginBottom: '5px'
      }
    }),
  );

  const [modalStyle] = React.useState(getModalStyle);
  const classes = useStyles();

  const click = ()=> {
    props.confirm(value);
    setValue('');
  };

  return <Modal
    open={props.isOpen}
    aria-labelledby="simple-modal-title"
    aria-describedby="simple-modal-description"
  >
    <div style={modalStyle} className={classes.paper}>
      <h3 id="simple-modal-title">Adicionar e-mail</h3>
      <TextField
        type="email"
        style={{margin: 10}}
        label="Email"
        fullWidth
        id="email"
        name="email"
        value={value}
        onChange={(event: any) => setValue(event.target.value)}
      />
      <Button color="primary" variant="contained" onClick={click}>Confirmar</Button>
    </div>
  </Modal>
};

ModalEmail.propTypes = {
  isOpen: PropTypes.bool,
  confirm: PropTypes.func
};

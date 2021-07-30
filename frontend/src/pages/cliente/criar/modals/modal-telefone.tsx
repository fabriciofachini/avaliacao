import React, { useEffect, useState } from 'react';
import { createStyles, Theme, Button, Modal, TextField, TextFieldProps, Select, MenuItem } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import PropTypes from 'prop-types';
import InputMask, {Props} from 'react-input-mask';

export const ModalTelefone = (props: any) => {

  const [value, setValue] = useState('');
  const [tipoTelefone, setTipoTelefone] = useState('RE');

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
  const classes: any = useStyles();

  const click = ()=> {
    props.confirm({
      numero: value,
      tipo: tipoTelefone
    });
    setValue('');
  };

  return <Modal
    open={props.isOpen}
    aria-labelledby="simple-modal-title"
    aria-describedby="simple-modal-description"
  >
    <div style={modalStyle} className={classes.paper}>
      <h3 id="simple-modal-title">Adicionar telefone</h3>
      <Select
        labelId="demo-simple-select-label"
        id="demo-simple-select"
        onChange={event => setTipoTelefone(event.target.value)}
        value={tipoTelefone}
        style={{display: 'block'}}
      >
        <MenuItem value={'RE'}>Residencial</MenuItem>
        <MenuItem value={'CO'}>Comercial</MenuItem>
        <MenuItem value={"CE"}>Celular</MenuItem>
      </Select>
      <InputMask
        mask={tipoTelefone === 'CE' ? "(99) 99999-9999" : "(99) 9999-9999"}
        value={value}
        onChange={(event: any) => setValue(event.target.value)}
      >
        {(inputProps: Props & TextFieldProps)=>
          <TextField
            {...inputProps}
            type="tel"
            label="Telefone"
            style={{margin: 10}}
            fullWidth
            id="telefone"
            name="telefone"
          />
        }
      </InputMask>
      <Button color="primary" variant="contained" onClick={click}>Confirmar</Button>
    </div>
  </Modal>
};

ModalTelefone.propTypes = {
  isOpen: PropTypes.bool,
  confirm: PropTypes.func
};

import React from 'react';
import LoginPage from './pages/login';
import PrivateRoute from './routes/private';
import { Redirect, Route, Router, Switch,  } from 'react-router-dom';
import ClientePage from './pages/cliente';
import { createBrowserHistory } from 'history';
import CriarClientePage from './pages/cliente/criar';
import { useAuth } from './context/auth';

const history = createBrowserHistory();

function App() {
  const { admin } = useAuth();
  return (
      <Router history={history}>
        <Switch>
            <Route path="/login" component={LoginPage}/>
            <PrivateRoute exact={true} path="/" component={ClientePage}/>
            {admin && <PrivateRoute exact path="/cliente" component={CriarClientePage}/>}
            {admin && <PrivateRoute exact path="/cliente/:id" component={CriarClientePage}/>}
            <Redirect to="/" />
        </Switch>
      </Router>
  );
}

export default App;

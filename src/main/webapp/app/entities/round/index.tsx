import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Round from './round';
import RoundDetail from './round-detail';
import RoundUpdate from './round-update';
import RoundDeleteDialog from './round-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoundUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoundUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoundDetail} />
      <ErrorBoundaryRoute path={match.url} component={Round} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RoundDeleteDialog} />
  </>
);

export default Routes;

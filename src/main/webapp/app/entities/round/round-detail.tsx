import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './round.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { DurationFormat } from 'app/shared/DurationFormat';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoundDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roundEntity = useAppSelector(state => state.round.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roundDetailsHeading">Round</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{roundEntity.id}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>{roundEntity.startDate ? <TextFormat value={roundEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{roundEntity.endDate ? <TextFormat value={roundEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="duration">Duration</span>
          </dt>
          <dd>
            {roundEntity.duration ? <DurationFormat value={roundEntity.duration} /> : null} ({roundEntity.duration})
          </dd>
          <dt>
            <span id="skillsRequired">Skills Required</span>
          </dt>
          <dd>{roundEntity.skillsRequired}</dd>
          <dt>
            <span id="link">Link</span>
          </dt>
          <dd>{roundEntity.link}</dd>
        </dl>
        <Button tag={Link} to="/round" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/round/${roundEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoundDetail;

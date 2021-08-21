import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyEntity = useAppSelector(state => state.company.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">Company</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="companyName">Company Name</span>
          </dt>
          <dd>{companyEntity.companyName}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>{companyEntity.startDate ? <TextFormat value={companyEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{companyEntity.endDate ? <TextFormat value={companyEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="companyDetails">Company Details</span>
          </dt>
          <dd>{companyEntity.companyDetails}</dd>
          <dt>
            <span id="placementType">Placement Type</span>
          </dt>
          <dd>{companyEntity.placementType}</dd>
          <dt>
            <span id="salaryPackage">Salary Package</span>
          </dt>
          <dd>{companyEntity.salaryPackage}</dd>
          <dt>
            <span id="stage">Stage</span>
          </dt>
          <dd>{companyEntity.stage}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyDetail;

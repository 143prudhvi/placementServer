import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const companyEntity = useAppSelector(state => state.company.entity);
  const loading = useAppSelector(state => state.company.loading);
  const updating = useAppSelector(state => state.company.updating);
  const updateSuccess = useAppSelector(state => state.company.updateSuccess);

  const handleClose = () => {
    props.history.push('/company');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...companyEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...companyEntity,
          startDate: convertDateTimeFromServer(companyEntity.startDate),
          endDate: convertDateTimeFromServer(companyEntity.endDate),
          placementType: 'INTERNSHIP',
          stage: 'NOTSTARTED',
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="placementServerApp.company.home.createOrEditLabel" data-cy="CompanyCreateUpdateHeading">
            Create or edit a Company
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="company-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Company Name" id="company-companyName" name="companyName" data-cy="companyName" type="text" />
              <ValidatedField
                label="Start Date"
                id="company-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="End Date"
                id="company-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Company Details"
                id="company-companyDetails"
                name="companyDetails"
                data-cy="companyDetails"
                type="text"
              />
              <ValidatedField label="Placement Type" id="company-placementType" name="placementType" data-cy="placementType" type="select">
                <option value="INTERNSHIP">INTERNSHIP</option>
                <option value="FULLTIME">FULLTIME</option>
              </ValidatedField>
              <ValidatedField label="Salary Package" id="company-salaryPackage" name="salaryPackage" data-cy="salaryPackage" type="text" />
              <ValidatedField label="Stage" id="company-stage" name="stage" data-cy="stage" type="select">
                <option value="NOTSTARTED">NOTSTARTED</option>
                <option value="STARTED">STARTED</option>
                <option value="ONGOING">ONGOING</option>
                <option value="COMPLETED">COMPLETED</option>
                <option value="SELECTED">SELECTED</option>
                <option value="REJECTEDBYME">REJECTEDBYME</option>
                <option value="NOOFFERRECEIVED">NOOFFERRECEIVED</option>
                <option value="NOTSELECTED">NOTSELECTED</option>
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/company" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CompanyUpdate;

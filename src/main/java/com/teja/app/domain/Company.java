package com.teja.app.domain;

import com.teja.app.domain.enumeration.Placement;
import com.teja.app.domain.enumeration.Stage;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "company_details")
    private String companyDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "placement_type")
    private Placement placementType;

    @Column(name = "salary_package")
    private String salaryPackage;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    private Stage stage;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company id(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Company startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Company endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getCompanyDetails() {
        return this.companyDetails;
    }

    public Company companyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
        return this;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
    }

    public Placement getPlacementType() {
        return this.placementType;
    }

    public Company placementType(Placement placementType) {
        this.placementType = placementType;
        return this;
    }

    public void setPlacementType(Placement placementType) {
        this.placementType = placementType;
    }

    public String getSalaryPackage() {
        return this.salaryPackage;
    }

    public Company salaryPackage(String salaryPackage) {
        this.salaryPackage = salaryPackage;
        return this;
    }

    public void setSalaryPackage(String salaryPackage) {
        this.salaryPackage = salaryPackage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public Company stage(Stage stage) {
        this.stage = stage;
        return this;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", companyDetails='" + getCompanyDetails() + "'" +
            ", placementType='" + getPlacementType() + "'" +
            ", salaryPackage='" + getSalaryPackage() + "'" +
            ", stage='" + getStage() + "'" +
            "}";
    }
}

package com.teja.app.domain;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Round.
 */
@Entity
@Table(name = "round")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "skills_required")
    private String skillsRequired;

    @Column(name = "link")
    private String link;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Round id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Round startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Round endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Round duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getSkillsRequired() {
        return this.skillsRequired;
    }

    public Round skillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
        return this;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public String getLink() {
        return this.link;
    }

    public Round link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Round)) {
            return false;
        }
        return id != null && id.equals(((Round) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Round{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", duration='" + getDuration() + "'" +
            ", skillsRequired='" + getSkillsRequired() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}

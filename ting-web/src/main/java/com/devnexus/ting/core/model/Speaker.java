package com.devnexus.ting.core.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * The persistent class for the speakers database table.
 *
 */
@Entity
@Cacheable()
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) //, include="non-lazy"
@FilterDefs({
		@FilterDef(name = "presentationFilter"),
		@FilterDef(name = "presentationFilterEventId", parameters=@ParamDef( name="eventId", type="long" ) )
		})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Speaker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@NotEmpty
	@Size(max=10000)
	private String bio;

	@Temporal(value=TemporalType.TIMESTAMP)
	@XmlTransient
	private Date createdAt;

	@NotEmpty
	@Size(max=255)
	private String firstName;

	@NotEmpty
	@Size(max=255)
	private String lastName;

	@Size(max=255)
	@XmlTransient
	private String picture;

	@Temporal(value=TemporalType.TIMESTAMP)
	@XmlTransient
	private Date updatedAt;

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="speaker")
    @Filters({
	    @Filter(name = "presentationFilter", condition = "EVENT = (select e.ID from EVENTS e where e.CURRENT = 'true')"),
	    @Filter(name = "presentationFilterEventId", condition = "EVENT = :eventId")
    })
    @XmlTransient
	private Set<Presentation> presentations = new HashSet<Presentation>(0);

    @ManyToMany(fetch=FetchType.LAZY, mappedBy="speakers")
    @XmlTransient
	private Set<Event>events = new HashSet<Event>(0);


    public Speaker() {
    }

	public Speaker(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getFullName() {
		return this.lastName + ", " + this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<Presentation> getPresentations() {
		return presentations;
	}

	public void setPresentations(Set<Presentation> presentations) {
		this.presentations = presentations;
	}

	@Override
	public String toString() {
		return "Speaker [firstName=" + firstName + ", id=" + id + ", lastName="
				+ lastName + "]";
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	/**
	 * @return the events
	 */
	public Set<Event> getEvents() {
		return events;
	}

}
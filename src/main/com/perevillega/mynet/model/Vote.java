package com.perevillega.mynet.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import org.hibernate.validator.Length;

@Entity
public class Vote implements Serializable
{
    private Long id;
    private Integer version;
    private String name;
    private Link link;
    private User user;
    private int value;


    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    @Length(max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @ManyToOne
	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Basic
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
    
}

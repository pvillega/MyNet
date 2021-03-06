/**
* My Net is free software, you can redistribute it and/or modify
* it under the terms of GNU Affero General Public License
* as published by the Free Software Foundation, either version 3
* of the License, or (at your option) any later version.
*
* You should have received a copy of the the GNU Affero
* General Public License, along with My Net. If not, see
*
* http://www.gnu.org/licenses/agpl.txt
*/

package com.perevillega.mynet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Roles;

import static com.perevillega.mynet.action.settings.Settings.*;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")
	})
@Name("user")
@Roles({
@org.jboss.seam.annotations.Role(name = "currentUser", scope = ScopeType.SESSION),
@org.jboss.seam.annotations.Role(name = "newuser", scope = ScopeType.CONVERSATION)
})
public class User implements Serializable
{
    private Long id;
    private Integer version;
    private String username;
    private String passwordHash;
    private String email;
	private List<Role> roles = new ArrayList<Role>(0);
	private List<Link> ownlinks = new ArrayList<Link>(0);
	private String description;
	private boolean selected;	
	private List<Link> favorites = new ArrayList<Link>(0);
	private List<Link> liked = new ArrayList<Link>(0);
	private List<Link> disliked = new ArrayList<Link>(0);
	

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

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

    @Column(name = "username", nullable = false)
    @Length(min= 6, max = 20)
    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	@NotNull	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {		
		this.passwordHash = passwordHash;
	}

	@Email
	@NotNull
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToMany(fetch = FetchType.LAZY)	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role){
		this.roles.add(role);
	}
	
	public void removeRole(Role role){
		this.roles.remove(role);
	}
	
	public void remove() {
		for(Role role: roles){
			role.removeUser(this);
		}
		for(Link l: favorites) {
			l.removeFavorite(this);
		}
		for(Link l: liked) {
			l.removeLike(this);
		}
		for(Link l: disliked) {
			l.removeDislike(this);
		}
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY)
	public List<Link> getOwnlinks() {
		return ownlinks;
	}

	public void setOwnlinks(List<Link> ownlinks) {
		this.ownlinks = ownlinks;
	}
	
	public List<Link> listPublicLinks() {
		List<Link> publiclinks = new ArrayList<Link>();
		for(Link l: ownlinks){
			if(!l.isHidden()) {
				publiclinks.add(l);
			}
		}
		return publiclinks;
	}
	
	@ManyToMany(mappedBy = "favorited", fetch=FetchType.LAZY)	
	public List<Link> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Link> favorites) {
		this.favorites = favorites;
	}	
	
	public void removeFavorite(Link link){
		this.favorites.remove(link);
	}
	
	public void addFavorite(Link link){
		this.favorites.add(link);
	}
	
	@ManyToMany(mappedBy = "like", fetch=FetchType.LAZY)
	public List<Link> getLiked() {
		return liked;
	}

	public void setLiked(List<Link> liked) {
		this.liked = liked;
	}

	public void removeLike(Link link){
		this.liked.remove(link);	
	}
	
	public void addLike(Link link){
		this.liked.add(link);
	}
	
	@ManyToMany(mappedBy = "dislike", fetch=FetchType.LAZY)
	public List<Link> getDisliked() {
		return disliked;
	}

	public void setDisliked(List<Link> disliked) {
		this.disliked = disliked;
	}
	
	public void removeDislike(Link link){
		this.disliked.remove(link);	
	}
	
	public void addDislike(Link link){
		this.disliked.add(link);
	}
}

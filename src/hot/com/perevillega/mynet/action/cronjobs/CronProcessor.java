package com.perevillega.mynet.action.cronjobs;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalCron;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.log.Log;

import com.perevillega.mynet.model.Category;
import com.perevillega.mynet.model.Link;
import com.perevillega.mynet.model.Role;
import com.perevillega.mynet.model.Tag;


@Name("processor")
@AutoCreate
@Scope(ScopeType.APPLICATION)
public class CronProcessor { 
 
    @Logger
    private Log log;
    
    @In(create=true)
    EntityManager entityManager;
 
    @Asynchronous
    @Transactional
    public QuartzTriggerHandle runJobs(@Expiration Date when, @IntervalCron String interval) {
    	
    	calculateTagsLinks(entityManager);    
    	calculateCategoryLinksandChildren(entityManager);
    	validateLinks(entityManager);
    	calculateRoleUsers(entityManager);
    	
    	entityManager.flush();
    	return null;
    }
    
    private void calculateTagsLinks(EntityManager em){
    	log.info("Tags - calculating links number for each tag");
		
		Query queryTags = em.createNamedQuery("findAllTags");
		//queryTagByName.setParameter("name", name);				
		Collection tags = queryTags.getResultList();
		
		if (!tags.isEmpty()) {
			Iterator it = tags.iterator();
			while(it.hasNext()) {
				Tag tag = (Tag) it.next();
				tag.setNumLinks(tag.getLinks().size());
			}
		}
    }
    
    private void calculateCategoryLinksandChildren(EntityManager em){
    	log.info("Categories - calculating links and children number for each category");
		
		Query queryCategories = em.createNamedQuery("findAllCategories");		
		Collection categories = queryCategories.getResultList();
		
		if (!categories.isEmpty()) {
			Iterator it = categories.iterator();
			while(it.hasNext()) {
				Category category = (Category) it.next();
				category.setNumLinks(category.getLinks().size());
				category.setNumChildren(category.getChildren().size());
			}
		}		
    }
    
    private void validateLinks(EntityManager em){
    	log.info("Links - validate URLs");
		
		Query queryLinks = em.createNamedQuery("findAllLinks");		
		Collection links = queryLinks.getResultList();
		
		if (!links.isEmpty()) {
			Iterator it = links.iterator();
			while(it.hasNext()) {
				Link link = (Link) it.next();
				link.validate();
			}
		}		
    }
    
    private void calculateRoleUsers(EntityManager em){
    	log.info("Roles - calculating users number for each role");
		
		Query queryRoles = em.createNamedQuery("findAllRoles");						
		Collection roles = queryRoles.getResultList();
		
		if (!roles.isEmpty()) {
			Iterator it = roles.iterator();
			while(it.hasNext()) {
				Role role = (Role) it.next();
				role.setNumUsers(role.getUsers().size());
			}
		}
    }
}
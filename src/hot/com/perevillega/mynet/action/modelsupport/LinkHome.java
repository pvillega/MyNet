package com.perevillega.mynet.action.modelsupport;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessage.Severity;

import com.perevillega.mynet.model.Link;
import com.perevillega.mynet.model.Tag;

@Name("linkHome")
public class LinkHome extends EntityHome<Link> {
	@RequestParameter
	Long linkId;

	@Override
	public Object getId() {
		if (linkId == null) {
			return super.getId();
		} else {
			return linkId;
		}
	}

	@Override
	public void create() {
		super.create();
	}

	@Override
	protected Link createInstance() {
		Link link = super.createInstance();
		link.setDate(new Date());

		return link;
	}

	@Override
	public String update() {
		addTags();
		return super.update();
	}

	@Override
	public String persist() {
		addTags();
		return super.persist();
	}

	private void addTags() {
		EntityManager em = this.getEntityManager();

		String taglist = instance.getTaglist();

		if (taglist != null) {
			StringTokenizer st = new StringTokenizer(taglist, ",");
			while (st.hasMoreTokens()) {
				String name = st.nextToken().trim();

				Query queryTagByName = em.createNamedQuery("findTagByName");
				queryTagByName.setParameter("name", name);
				Collection tags = queryTagByName.getResultList();

				if (!tags.isEmpty()) {
					// existing tag, we only take the first one
					Iterator it = tags.iterator();
					Tag tag = (Tag) it.next();
					instance.addTag(tag);
				} else {
					// a new tag
					Tag tag = new Tag();
					tag.setName(name);
					tag.addLink(instance);
					instance.addTag(tag);
					em.persist(tag);
				}
			}
		}
	}

	@Override
	public String remove() {
		getInstance().remove();
		return super.remove();
	}
}

package com.perevillega.mynet.action.modelsupport;

import static com.perevillega.mynet.action.settings.Settings.MAX_RESULTS;

import java.util.Arrays;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Link;
import com.perevillega.mynet.model.Category;

@Name("linkList")
@Scope(ScopeType.CONVERSATION)
public class LinkList extends EnhancedSortEntityQuery<Link>
{
	private static final String[] RESTRICTIONS = {		
		"lower(link.name) like concat('%',lower(#{linkList.name}),'%')",
		"lower(link.url) like concat('%',lower(#{linkList.url}),'%')",
		"lower(link.creator.username) like concat('%',lower(#{linkList.creator}),'%')",
		"lower(link.description) like concat('%',lower(#{linkList.description}),'%')",
		"link.category = #{linkList.category}",
		};

	
	private String name;	
	private String url;
	private String creator;
	private String description;
	private Category category;
	
	private String title;
	private String from;
	private boolean create;	
	
    public LinkList()
    {
        setEjbql("select link from Link link");
        setMaxResults(MAX_RESULTS);
        setOrder("link.date desc");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));       
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void cleanSearch() {
		this.name = null;
		this.url = null;
		this.creator = null;
		this.description = null;
		this.category = null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}

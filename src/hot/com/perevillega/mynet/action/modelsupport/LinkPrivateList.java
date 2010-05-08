package com.perevillega.mynet.action.modelsupport;

import static com.perevillega.mynet.action.settings.Settings.MAX_RESULTS;

import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Link;

@Name("linkPrivateList")
public class LinkPrivateList extends EnhancedSortEntityQuery<Link>
{
    public LinkPrivateList()
    {
        setEjbql("select link from Link link where link.hidden = true");
        setMaxResults(MAX_RESULTS);
        setOrder("link.date desc");
        
    }
    
}
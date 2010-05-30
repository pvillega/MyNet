package com.perevillega.mynet.action.modelsupport;

import static com.perevillega.mynet.action.settings.Settings.MAX_RESULTS;

import java.util.Arrays;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Tag;

@Name("popularTagsList")
@Scope(ScopeType.CONVERSATION)
public class PopularTagList extends EnhancedSortEntityQuery<Tag>
{

    public PopularTagList()
    {
        setEjbql("select tag from Tag tag");
        setMaxResults(50);
        setOrder("tag.numLinks desc");        
    }

}

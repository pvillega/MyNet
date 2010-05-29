package com.perevillega.mynet.action.modelsupport;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import com.perevillega.mynet.model.Vote;

@Name("voteList")
public class VoteList extends EntityQuery<Vote>
{
    public VoteList()
    {
        setEjbql("select vote from Vote vote");
    }
}

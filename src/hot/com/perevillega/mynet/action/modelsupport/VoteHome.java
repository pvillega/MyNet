package com.perevillega.mynet.action.modelsupport;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import com.perevillega.mynet.model.Vote;

@Name("voteHome")
public class VoteHome extends EntityHome<Vote>
{
    @RequestParameter Long voteId;

    @Override
    public Object getId()
    {
        if (voteId == null)
        {
            return super.getId();
        }
        else
        {
            return voteId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}

package com.perevillega.mynet.action;

import java.util.UUID;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.action.modelsupport.LinkList;
import com.perevillega.mynet.action.rss.Feed;

@Name("rss")
public class RSSAction
{
	
   private Feed feed;
   
     
   @Create
   public void create()
   {
      feed = new Feed();
      //our default linkList is already ordered by date, desc, and limited to 50 results
      LinkList linklist = new LinkList();
      
      feed.setEntries(linklist.getResultList());
      feed.setLink("Links Feed");
      feed.setSubtitle("Most recent links feed");
      feed.setTitle("MyNET Links Feed");
      feed.setUid(UUID.randomUUID().toString());
      
      //feed updated at the time of the last link sent
      feed.setUpdated(linklist.getResultList().get(0).getDate());
   }

   public Feed getFeed()
   {
      return feed;
   }

}

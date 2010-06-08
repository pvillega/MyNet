package com.perevillega.mynet.action.rss;


import java.util.Date;
import java.util.List;

import com.perevillega.mynet.model.Link;

public class Feed
{
   private String uid;
   private String title;
   private String subtitle;
   private Date updated;
   private String link;
   private List<Link> entries;

   public String getUid()
   {
      return uid;
   }

   public void setUid(String uid)
   {
      this.uid = uid;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public String getSubtitle()
   {
      return subtitle;
   }

   public void setSubtitle(String subtitle)
   {
      this.subtitle = subtitle;
   }

   public Date getUpdated()
   {
      return updated;
   }

   public void setUpdated(Date updated)
   {
      this.updated = updated;
   }

   public String getLink()
   {
      return link;
   }

   public void setLink(String link)
   {
      this.link = link;
   }

   public List<Link> getEntries()
   {
      return entries;
   }

   public void setEntries(List<Link> entries)
   {
      this.entries = entries;
   }

}

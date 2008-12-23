/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.tutorial.callback.bean;

import javax.persistence.PreRemove;
import javax.persistence.PostRemove;
import javax.persistence.PreUpdate;
import javax.persistence.PostUpdate;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PostPersist;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class CustomerCallbackListener
{
   @PrePersist
   public void doPrePersist(Customer customer)
   {
      System.out.println("doPrePersist: About to create Customer: " + customer.getFirst() + " " + customer.getLast());
   }

   @PostPersist
   public void doPostPersist(Object customer)
   {
      System.out.println("doPostPersist: Created Customer: " + ((Customer)customer).getFirst() + " " + ((Customer)customer).getLast());
   }

   @PreRemove
   public void doPreRemove(Customer customer)
   {
      System.out.println("doPreRemove: About to delete Customer: " + customer.getFirst() + " " + customer.getLast());
   }

   @PostRemove
   public void doPostRemove(Customer customer)
   {
      System.out.println("doPostRemove: Deleted Customer: " + customer.getFirst() + " " + customer.getLast());
   }

   @PreUpdate
   public void doPreUpdate(Customer customer)
   {
      System.out.println("doPreUpdate: About to update Customer: " + customer.getFirst() + " " + customer.getLast());
   }

   @PostUpdate
   public void doPostUpdate(Customer customer)
   {
      System.out.println("doPostUpdate: Updated Customer: " + customer.getFirst() + " " + customer.getLast());
   }

   @PostLoad
   public void doPostLoad(Customer customer)
   {
      System.out.println("doPostLoad: Loaded Customer: " + customer.getFirst() + " " + customer.getLast());
   }


}

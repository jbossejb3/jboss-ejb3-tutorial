/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.tutorial.jndibinding.bean;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.logging.Logger;

/**
 * CustomerBean
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
@Stateless
@Remote(CustomerManager.class)
@RemoteBinding(jndiBinding = "CustomerManager")
public class CustomerBean implements CustomerManager
{
   // Logger
   private Logger logger = Logger.getLogger(CustomerBean.class);

   // must match the one in persistence.xml
   private static final String ENTITY_MANAGER_JNDI_NAME = "java:/jboss-ejb3-tutorial-jndibinding-EntityManager";
   
   public long createCustomer(String name)
   {
      Customer customer = new Customer();
      customer.setName(name);
      // get the entity manager from JNDI
      try
      {
         Context ctx = new InitialContext();
         EntityManager em = (EntityManager) ctx.lookup(ENTITY_MANAGER_JNDI_NAME);
         logger.debug("Entitymanager found in jndi: " + em);
         em.persist(customer);
         logger.debug("Created customer named " + name + " with id " + customer.getId());
         return customer.getId();
      }
      catch (NamingException ne)
      {
         logger.error("Could not create customer with name " + name, ne);
         throw new RuntimeException(ne);
      }
   }

   public Customer getCustomer(long id)
   {
      try
      {
         Context ctx = new InitialContext();
         EntityManager em = (EntityManager) ctx.lookup(ENTITY_MANAGER_JNDI_NAME);
         logger.debug("Entitymanager found in jndi: " + em);
         return em.find(Customer.class,id);
      }
      catch (NamingException ne)
      {
         logger.error("Could not find customer with id " + id, ne);
         throw new RuntimeException(ne);
      } 
   }

}

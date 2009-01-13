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
package org.jboss.tutorial.cachedentity.bean;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision$
 */
@Stateless
@Remote(EntityTest.class)
public class EntityTestBean implements EntityTest
{
   @PersistenceContext
   private EntityManager manager;
   
   /**
    * Logger
    */
   private Logger logger = Logger.getLogger(EntityTestBean.class);

   public Customer createCustomer()
   {
      Customer customer = new Customer();
      customer.setName("JBoss");

      Set<Contact> contacts = new HashSet<Contact>();
      Contact kabir = new Contact();
      kabir.setCustomer(customer);
      kabir.setName("Kabir");
      kabir.setTlf("1111");
      contacts.add(kabir);

      Contact bill = new Contact();
      bill.setCustomer(customer);
      bill.setName("Bill");
      bill.setTlf("2222");
      contacts.add(bill);

      customer.setContacts(contacts);
      manager.persist(customer);
      logger.info("Created customer named " + customer.getName() + " with " + customer.getContacts().size() + " contacts");
      return customer;
   }

   public Customer findByCustomerId(Long id)
   {
      logger.info("Find customer with id = " + id);
      Customer customer = manager.find(Customer.class, id);
      logger.info("Customer with id = " + id + " found");
      return customer;
   }
   
   

}

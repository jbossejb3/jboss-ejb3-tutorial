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
package org.jboss.tutorial.extended.bean;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
@Stateful
@Remote(ShoppingCart.class)
public class ShoppingCartBean implements ShoppingCart
{
   @PersistenceContext(type=PersistenceContextType.EXTENDED) EntityManager em;

   @EJB StatelessLocal stateless;

   private Customer customer;

   public long createCustomer()
   {
      customer = new Customer();
      customer.setName("William");
      em.persist(customer);
      return customer.getId();
   }

   public void update()
   {
      customer.setName("Bill");
   }
   public void update2()
   {
      customer.setName("Billy");
   }

   public void update3()
   {
      stateless.update(customer);
   }

   public Customer find(long id)
   {
      return em.find(Customer.class, id);
   }

   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   public void never()
   {
      customer.setName("Bob");
   }

   

   @Remove
   public void checkout()
   {
   }
}

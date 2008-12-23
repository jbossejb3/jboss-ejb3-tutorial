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
package org.jboss.tutorial.composite.bean;

import java.util.HashSet;
import java.util.Set;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContext;

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
   private @PersistenceContext EntityManager manager;
   private static long genid = 0;

   public void manyToManyCreate() throws Exception
   {

      Flight firstOne = new Flight();
      firstOne.setId(new Long(1));
      firstOne.setName("AF0101");
      manager.persist(firstOne);
      Flight second = new Flight();
      second.setId(new Long(2));
      second.setName("US1");

      Set<Customer> customers1 = new HashSet<Customer>();
      Set<Customer> customers2 = new HashSet<Customer>();


      Customer bill = new Customer();
      CustomerPK pk = new CustomerPK(genid++, "Bill");
      bill.setPk(pk);
      customers1.add(bill);

      Customer monica = new Customer();
      CustomerPK moPK = new CustomerPK(genid++, "Monica");
      monica.setPk(moPK);
      customers1.add(monica);

      Customer molly = new Customer();
      CustomerPK mollyPK = new CustomerPK(genid++, "Molly");
      molly.setPk(mollyPK);
      customers2.add(molly);

      firstOne.setCustomers(customers1);
      second.setCustomers(customers2);

      manager.persist(second);
   }


   public Flight findFlightById(Long id) throws Exception
   {
      return manager.find(Flight.class, id);
   }

}

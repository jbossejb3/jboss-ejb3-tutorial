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
package org.jboss.tutorial.hibernate.bean;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.logging.Logger;
import org.jboss.tutorial.hibernate.Customer;

/**
 * CustomerBean
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
@Stateless
@Remote(CustomerRemote.class)
@RemoteBinding(jndiBinding = "CustBean")
public class CustomerBean implements CustomerRemote
{

   private static Logger logger = Logger.getLogger(CustomerBean.class);

   @PersistenceContext
   private Session session;

   public long createCustomer(String fname, String lname)
   {
      Customer customer = new Customer();
      customer.setFname(fname);
      customer.setLname(lname);
      this.session.persist(customer);
      logger.info("Created new customer with name = " + fname + " " + lname + " with id = " + customer.getId());
      return customer.getId();
   }

   public Customer getCustomer(long id)
   {
      return (Customer) this.session.get(Customer.class, id);
   }

   public List<Customer> getCustomers(String fname)
   {
      Query hql = this.session.createQuery("from Customer where fname = '" + fname + "'");
      return hql.list();
   }

}

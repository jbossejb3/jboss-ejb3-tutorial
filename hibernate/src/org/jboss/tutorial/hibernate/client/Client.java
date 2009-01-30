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
package org.jboss.tutorial.hibernate.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.tutorial.hibernate.bean.CustomerRemote;

import org.jboss.tutorial.hibernate.Customer;

/**
 * Client
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class Client
{

   public static void main(String args[]) throws Exception
   {
      Context ctx = new InitialContext();
      CustomerRemote customerBean = (CustomerRemote) ctx.lookup("CustBean");
      // let's create customer#1
      long id = customerBean.createCustomer("Jai", "Pai");
      System.out.println("Jai Pai created with id = " + id);

      //let's create customer#2
      long custId2 = customerBean.createCustomer("Jaikiran", "Pai");
      System.out.println("Jaikiran Pai created with id = " + custId2);

      //one more customer
      long custId3 = customerBean.createCustomer("Jai", "NoLastName");
      System.out.println("Jai NoLastName created with id = " + custId3);

      // now let's find customer with some specific id
      System.out.println("Searching for customer with id = " + custId2);
      Customer customer = customerBean.getCustomer(custId2);
      System.out.println("Found customer " + customer.getFname() + " " + customer.getLname() + " with id = " + custId2);

      
      //let's use the other bean to search another customer
      CustomerRemote anotherCustomerBean = (CustomerRemote) ctx.lookup("AnotherCustBean");
      System.out.println("Searching for customer with id = " + custId3);
      Customer oneMoreCustomer = anotherCustomerBean.getCustomer(custId3);
      System.out.println("Found customer " + oneMoreCustomer.getFname() + " " + oneMoreCustomer.getLname() + " with id = " + custId3);

      // now let's find customers with first name=Jai
      System.out.println("Searching for customers with first name Jai");
      List<Customer> customers = customerBean.getCustomers("Jai");
      System.out.println("Found " + customers.size() + " customers with first name Jai");
      
      // let's use the other bean to find customers with name Jaikiran
      System.out.println("Searching for customers with first name Jaikiran");
      List<Customer> moreCustomers = anotherCustomerBean.getCustomers("Jaikiran");
      System.out.println("Found " + moreCustomers.size() + " customers with first name Jaikiran");
      

   }
}

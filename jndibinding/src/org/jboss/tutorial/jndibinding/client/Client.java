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
package org.jboss.tutorial.jndibinding.client;

import javax.naming.InitialContext;

import org.jboss.tutorial.jndibinding.bean.Calculator;
import org.jboss.tutorial.jndibinding.bean.Customer;
import org.jboss.tutorial.jndibinding.bean.CustomerManager;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      Calculator calculator = (Calculator) ctx.lookup("Calculator");

      System.out.println("1 + 1 = " + calculator.add(1, 1));
      System.out.println("1 - 1 = " + calculator.subtract(1, 1));
      
      // now let's use the customer bean to ensure that the entity manager is bound
      // to jndi
      CustomerManager customerManager = (CustomerManager) ctx.lookup("CustomerManager");
      long id = customerManager.createCustomer("Jaikiran");
      System.out.println("Created customer with id = " + id);
      Customer customer = customerManager.getCustomer(id);
      System.out.println("Customer's name is " + customer.getName());
   }
}

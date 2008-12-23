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
package org.jboss.tutorial.extended.client;

import javax.naming.InitialContext;
import org.jboss.tutorial.extended.bean.Customer;
import org.jboss.tutorial.extended.bean.ShoppingCart;
import org.jboss.tutorial.extended.bean.StatelessRemote;

//import java.rmi.*;


/**
 * Sample client for the jboss container.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Id$
 */

public class Client
{
   public static InitialContext getInitialContext()  throws Exception
   {
      return new InitialContext();
   }

   public static void testLongLivedSession() throws Exception
   {
      ShoppingCart test = (ShoppingCart) getInitialContext().lookup("ShoppingCartBean/remote");
      StatelessRemote remote = (StatelessRemote) getInitialContext().lookup("StatelessSessionBean/remote");
      Customer c;

      long id = test.createCustomer();
      c = remote.find(id);
      System.out.println("Created customer: " + c.getName());

      test.update();
      c = remote.find(id);
      System.out.println("ShoppingCartBean.customer should stay managed because we're in an extended PC: Customer.getName() == " + c.getName());

      test.update3();
      c = remote.find(id);
      System.out.println("Extended persistence contexts are propagated to nested EJB calls: Customer.getName() == " + c.getName());
      test.checkout();
   }

   public static void testWithFlushMode() throws Exception
   {
      ShoppingCart cart = (ShoppingCart) getInitialContext().lookup("ShoppingCartBean/remote");
      StatelessRemote dao = (StatelessRemote) getInitialContext().lookup("StatelessSessionBean/remote");
      Customer c;
      long id;


      id = cart.createCustomer();
      c = dao.find(id);
      System.out.println("Created customer: " + c.getName());

      cart.never();
      c = dao.find(id);
      System.out.println("Customer's name should still be William as pc was not yet flushed:  Customer.getName() == " + c.getName());

      cart.checkout();
      c = dao.find(id);
      System.out.println("Now that the pc has been flushed name should be 'Bob': Customer.getName() == " + c.getName());

   }

   public static void main(String[] args) throws Exception
   {
      testWithFlushMode();
      testLongLivedSession();
   }
}

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
package org.jboss.tutorial.stateful_deployment_descriptor.client;


import org.jboss.tutorial.stateful_deployment_descriptor.bean.ShoppingCart;

import javax.ejb.EJBException;
import javax.ejb.NoSuchEJBException;
import javax.naming.InitialContext;

import java.rmi.NoSuchObjectException;
import java.util.HashMap;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision$
 */
public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      ShoppingCart cart = (ShoppingCart) ctx.lookup(ShoppingCart.class.getName());

      System.out.println("Buying 1 memory stick");
      cart.buy("Memory stick", 1);
      System.out.println("Buying another memory stick");
      cart.buy("Memory stick", 1);

      System.out.println("Buying a laptop");
      cart.buy("Laptop", 1);

      System.out.println("Print cart:");
      HashMap<String, Integer> fullCart = cart.getCartContents();
      for (String product : fullCart.keySet())
      {
         System.out.println(fullCart.get(product) + "     " + product);
      }

      System.out.println("Checkout");
      cart.checkout();

      System.out.println("Should throw an object not found exception by invoking on cart after @Remove method");
      try
      {
         cart.getCartContents();
      }
      catch (NoSuchEJBException e)
      {
            System.out.println("Successfully caught no such object exception.");
      }


   }
}

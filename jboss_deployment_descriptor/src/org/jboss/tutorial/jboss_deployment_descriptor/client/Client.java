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
package org.jboss.tutorial.jboss_deployment_descriptor.client;

import java.util.HashMap;

import javax.ejb.NoSuchEJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.security.SecurityAssociation;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;
import org.jboss.tutorial.jboss_deployment_descriptor.bean.ShoppingCart;
import org.jboss.tutorial.jboss_deployment_descriptor.bean.StatelessTest;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 61136 $
 */
public class Client
{
   public static void main(String[] args) throws Exception
   {
      testShoppingCart();
      testStateless();
   }

   private static void testShoppingCart() throws Exception
   {
      // Establish the proxy with an incorrect security identity
      SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
      securityClient.setSimple("bill", "incorrectpassword");
      securityClient.login();
      
      Context ctx = new InitialContext();
      ShoppingCart cart = (ShoppingCart) ctx.lookup("ShoppingCart");

      System.out.println("Attempting to buy 1 memory stick with incorrect password");
      try
      {
         cart.buy("Memory stick", 1);
         throw new RuntimeException("ERROR - User with incorrect password was able to access the bean");
      }
      catch (javax.ejb.EJBAccessException e)
      {
         System.out.println("Caught javax.ejb.EJBAccessException as expected");
      }

      System.out.println("Setting user/password");
      securityClient.logout();
      
      securityClient.setSimple("bill", "password-test");
      securityClient.login();

      System.out.println("bill is a shopper, so is allowed to buy");
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

      System.out.println("bill is not a clerk, so is not allowed to price check");
      try
      {
         cart.priceCheck("Laptop");
         throw new RuntimeException("ERROR - User with insufficient access rights allowed to access bean method");
      }
      catch (javax.ejb.EJBAccessException ex)
      {
         System.out.println("Caught EJBAccessException as expected");
      }

      System.out.println("Checkout");
      cart.checkout();

      System.out.println("Should throw an object not found exception by invoking on cart after @Remove method");
      try
      {
         cart.getCartContents();
         throw new RuntimeException("ERROR - Bean not discarded");
      }
      catch (NoSuchEJBException e)
      {
         System.out.println("Successfully caught no such object exception.");
      }
      
      // logout the user
      securityClient.logout();
   }

   private static void testStateless() throws Exception
   {
      InitialContext jndiContext = new InitialContext();
      StatelessTest stateless = (StatelessTest) jndiContext.lookup("StatelessTest");

      SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
      securityClient.setSimple("bill2", "invalidpassword");
      securityClient.login();
      
      try
      {
         stateless.testSecurity();
         throw new RuntimeException("ERROR - User with incorrect password accessed the bean");
      }
      catch (javax.ejb.EJBAccessException e)
      {
         System.out.println("Caught javax.ejb.EJBAccessException, for SLSB, as expected");
      }

      // logout and login back with correct credentials
      System.out.println("Now passing the correct user/password to access the SLSB");
      securityClient.logout();
      securityClient.setSimple("bill2", "password-default");
      securityClient.login();
      
      // call the bean method
      stateless.testSecurity();

      System.out.println("Successfully accessed SLSB");
   }
}

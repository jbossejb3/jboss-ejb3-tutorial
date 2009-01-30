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
package org.jboss.tutorial.ssl.client;

import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;

import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;
import org.jboss.tutorial.ssl.bean.Calculator;

/**
 * @version $Revision: 61136 $
 */
public class Client
{
   public static void main(String[] args) throws Exception
   {
      // Establish the proxy with an incorrect security identity
      SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
      securityClient.setSimple("kabir","invalidpassword");
      securityClient.login();
      
      InitialContext ctx = new InitialContext();
      // let's use the bean whose proxy communicates via ssl
      Calculator sslCalculator = (Calculator) ctx.lookup("CalculatorBean/remote");
      System.out.println("Invoking bean methods on a proxy through sslsocket");
      System.out.println("Kabir is a student.");
      System.out.println("Kabir types in the wrong password");
      try
      {
         System.out.println("1 + 1 = " + sslCalculator.add(1, 1));
         throw new RuntimeException("ERROR - User with incorrect password allowed to operate on bean");
      }
      catch (EJBAccessException ex)
      {
         System.out.println("Saw expected SecurityException: " + ex.getMessage());
      }

      System.out.println("Kabir types in correct password.");
      System.out.println("Kabir does unchecked addition.");

      // Re-establish the proxy with the correct security identity
      securityClient.logout();
      securityClient.setSimple("kabir", "validpassword");
      securityClient.login();
      

      System.out.println("1 + 1 = " + sslCalculator.add(1, 1));

      System.out.println("Kabir is not a teacher so he cannot do division");
      try
      {
         sslCalculator.divide(16, 4);
         throw new RuntimeException("ERROR - User with insufficient role was allowed to operate on bean");
      }
      catch (EJBAccessException  ex)
      {
         System.out.println("Caught expected EJBAccessException : " + ex.getMessage());
      }

      System.out.println("Kabir is a student, and students are allowed to do subtraction");
      System.out.println("1 - 1 = " + sslCalculator.subtract(1, 1));
      
      // Now let's use the bean whose proxy communicates via normal socket: protocol
      Calculator normalCalculator = (Calculator) ctx.lookup("CalculatorNormal");
      // do some operation
      System.out.println("Invoking bean methods on a proxy through normal socket");
      System.out.println("Kabir is a student. So he can do addition");
      System.out.println("2 + 2 = " + normalCalculator.add(2, 2));
   }
}

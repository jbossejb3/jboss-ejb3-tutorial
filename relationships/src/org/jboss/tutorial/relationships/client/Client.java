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
package org.jboss.tutorial.relationships.client;

import org.jboss.tutorial.relationships.bean.Customer;
import org.jboss.tutorial.relationships.bean.EntityTest;
import org.jboss.tutorial.relationships.bean.Flight;

import javax.naming.InitialContext;

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
      EntityTest test = (EntityTest) ctx.lookup("EntityTestBean/remote");
      test.manyToManyCreate();

      Flight one = test.findFlightById(new Long(1));

      Flight two = test.findFlightById(new Long(2));

      System.out.println("Air France customers");
      for (Customer c : one.getCustomers())
      {
         System.out.println(c.getName());

      }
      System.out.println("USAir customers");

      for (Customer c : two.getCustomers())
      {
         System.out.println(c.getName());
      }

   }
}

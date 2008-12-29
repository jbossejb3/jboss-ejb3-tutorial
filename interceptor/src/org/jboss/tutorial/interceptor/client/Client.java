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
package org.jboss.tutorial.interceptor.client;

import org.jboss.tutorial.interceptor.bean.EmailSystem;
import javax.naming.InitialContext;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      System.out.println("Starting");
      InitialContext ctx = new InitialContext();
      EmailSystem emailSystem = (EmailSystem)ctx.lookup("EmailSystemBean/remote");
      
      System.out.println("\nCalling emailLostPassword");
      emailSystem.emailLostPassword("whatever");
      System.out.println("Waiting 2 seconds");
      Thread.sleep(2000);

      System.out.println("\nCalling sendBookingConfirmationMessage");
      emailSystem.sendBookingConfirmationMessage(100);
      System.out.println("Waiting 2 seconds");
      Thread.sleep(2000);

      System.out.println("\nCalling sendBookingConfirmationMessage");
      emailSystem.sendBookingConfirmationMessage(100);
      System.out.println("Waiting 2 seconds");
      Thread.sleep(2000);

      System.out.println("\nCalling sendBookingCancellationMessage");
      emailSystem.sendBookingCancellationMessage(100);
      System.out.println("Waiting 2 seconds");
      Thread.sleep(2000);

      System.out.println("\nCalling noop");
      emailSystem.noop();
      System.out.println("Waiting 2 seconds");
      Thread.sleep(2000);

      System.out.println("\nCalling noop2");
      emailSystem.noop2();
      System.out.println("Waiting 2 seconds");
      Thread.sleep(2000);


      System.out.println("Done");
   }
}

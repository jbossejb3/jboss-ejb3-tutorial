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
package org.jboss.tutorial.timer.bean;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;

@Stateless
@Remote(ExampleTimer.class)
public class ExampleTimerBean implements ExampleTimer
{
   private @Resource SessionContext ctx;

   public void scheduleTimer(long milliseconds)
   {
      ctx.getTimerService().createTimer(new Date(new Date().getTime() + milliseconds), "Hello World");
      System.out.println("---------------------");
      System.out.println("Created a timer event to be triggered after " + milliseconds + " milli seconds");
      System.out.println("---------------------");
   }

   @Timeout
   public void timeoutHandler(Timer timer)
   {
      System.out.println("---------------------");
      System.out.println("* Received Timer event: " + timer.getInfo());
      System.out.println("---------------------");

      timer.cancel();
   }
}

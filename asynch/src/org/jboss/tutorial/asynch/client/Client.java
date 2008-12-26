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
package org.jboss.tutorial.asynch.client;

import java.util.concurrent.Future;

import javax.naming.InitialContext;

import org.jboss.ejb3.common.proxy.plugins.async.AsyncUtils;
import org.jboss.tutorial.asynch.bean.Echo;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      Echo echo = (Echo) ctx.lookup("EchoBean/remote");
      System.out.println("-------- Synchronous call");
      String ret = echo.echo("normal call");
      System.out.println(ret);

      // Create the asynchronous proxy
      Echo asynchEcho = AsyncUtils.mixinAsync(echo);
      System.out.println("-------- Asynchronous call");
      ret = asynchEcho.echo("asynchronous call");
      System.out.println("Direct return of async invocation is: " + ret);

      System.out.println("-------- Synchronous call");
      ret = echo.echo("normal call 2");
      System.out.println(ret);

      System.out.println("-------- Result of Asynchronous call");
      Future<String> future = (Future<String>) AsyncUtils.getFutureResult(asynchEcho);

      // blocking call
      ret = (String) future.get();
      System.out.println(ret);

   }
}

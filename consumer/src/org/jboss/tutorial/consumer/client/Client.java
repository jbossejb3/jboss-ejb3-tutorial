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
package org.jboss.tutorial.consumer.client;

import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import org.jboss.ejb3.mdb.ProducerManager;
import org.jboss.ejb3.mdb.ProducerObject;
import org.jboss.ejb3.mdb.ProducerConfig;
import org.jboss.tutorial.consumer.bean.ExampleProducerRemote;
import org.jboss.tutorial.consumer.bean.Tester;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      ExampleProducerRemote remote = (ExampleProducerRemote) ctx.lookup(ExampleProducerRemote.class.getName());

      // you can typecast the returned proxy to obtain a ProducerManager interface that allows you to manage
      // interaction with JMS.
      ProducerManager manager = ((ProducerObject) remote).getProducerManager();


      // connect
      manager.connect();

      try
      {
         // Call method1
         remote.method1("Remote method1 called", 1);
         System.out.println("Remote method1 called");
         
         // Call method2
         Map<String, String> map = new HashMap<String, String>();
         map.put("hello", "world");
         map.put("great", "ejb3");

         remote.method2("Remote method2 called", map);
         System.out.println("Remote method2 called");
      }
      finally
      {
         // instead of typecasting, you can use a helper class that does everything for you.
         ProducerConfig.close(remote);
      }


      // Try out local producers by interfacing with Session bean
      Tester tester = (Tester) ctx.lookup("TesterBean/remote");
      tester.testLocal();
      tester.testXA();
   }
}

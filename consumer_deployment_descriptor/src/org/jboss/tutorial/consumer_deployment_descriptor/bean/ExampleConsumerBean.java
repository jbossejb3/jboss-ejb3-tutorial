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
package org.jboss.tutorial.consumer_deployment_descriptor.bean;

import java.util.Map;

import javax.jms.Message;

public class ExampleConsumerBean implements ExampleProducerRemote, ExampleProducerLocal, ExampleProducerXA
{
   // you can have container inject the current JMS message so that you can manipulate it, like for instance
   // to get a reply-to destination.
   private Message currentMessage;

   public void method1(String msg, int val)
   {
      System.out.println("method1(" + msg + ", " + val + ")");
   }
   public void method2(String msg, Map<String, String> map)
   {
      System.out.println("method2: " + msg);
      for (String key : map.keySet())
      {
         System.out.println("method2 key/val: " + key + ":" + map.get(key));
      }
   }

}

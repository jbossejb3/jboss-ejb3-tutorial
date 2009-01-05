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
package org.jboss.tutorial.mdb.client;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      processMessage("queue/tutorial/example");
      processMessage("queue/tutorial/defaultedexample");
   }

   private static void processMessage(String queueBinding) throws Exception
   {

      QueueConnection cnn = null;
      QueueSender sender = null;
      QueueSession session = null;
      try
      {
         InitialContext ctx = new InitialContext();
         Queue queue = (Queue) ctx.lookup(queueBinding);
         QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
         cnn = factory.createQueueConnection();
         session = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

         TextMessage msg = session.createTextMessage("Hello World");

         sender = session.createSender(queue);
         sender.send(msg);
         System.out.println("Message sent successfully to remote queue " + queueBinding);
      }
      finally
      {
         //cleanup
         if (cnn != null)
         {
            cnn.close();
         }

      }

   }
}

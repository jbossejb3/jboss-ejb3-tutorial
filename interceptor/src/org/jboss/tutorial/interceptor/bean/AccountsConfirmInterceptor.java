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
package org.jboss.tutorial.interceptor.bean;

import java.util.Date;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class AccountsConfirmInterceptor extends AccountsInterceptor
{
   @Resource(mappedName="java:ConnectionFactory")
   QueueConnectionFactory cf;

   @Resource(mappedName="queue/tutorial/accounts")
   Queue queue;

   @PersistenceContext
   EntityManager em;

   QueueConnection conn;

   public Object intercept(InvocationContext ctx) throws Exception
   {
      //overrides AccountsInterceptor.intercept() so that will not be invoked
      return null;
   }


   @AroundInvoke
   public Object sendConfirmMessage(InvocationContext ctx) throws Exception
   {
      QueueSession session = null;
      try
      {
         System.out.println("*** AccountsConfirmInterceptor intercepting");

         long orderId = (Long)ctx.getParameters()[0];

         if (em.find(Confirmation.class, orderId) == null)
         {
            System.out.println("*** AccountsConfirmInterceptor - recording confirmation");
            Confirmation confirmation = new Confirmation(orderId, new Date());
            em.persist(confirmation);
         }
         else
         {
            System.out.println("*** AccountsConfirmInterceptor - order has already been confirmed aborting");
            return null;
         }

         System.out.println("*** AccountsConfirmInterceptor - notifying accounts dept " + ctx.getMethod().getName());
         session = getConnection().createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
         Message msg = session.createTextMessage("Confirming order " + orderId);
         QueueSender sender = session.createSender(queue);
         sender.send(msg);

         return ctx.proceed();
      }
      catch(Exception e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         try{session.close();}catch(Exception e) {}
         System.out.println("*** AccountsConfirmInterceptor exiting");
      }
   }

   QueueConnection getConnection() throws JMSException
   {
      if (conn == null)
      {
         synchronized(cf)
         {
            if (conn == null)
            {
               conn = cf.createQueueConnection();
            }
         }
      }

      return conn;
   }
}

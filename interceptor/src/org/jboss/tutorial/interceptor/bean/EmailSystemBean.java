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

import javax.interceptor.AroundInvoke;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

@Stateless
@Interceptors ({TracingInterceptor.class})
@Remote(EmailSystem.class)
public class EmailSystemBean implements EmailSystem
{
   public void emailLostPassword(String username)
   {
      System.out.println("<In EmailSystemBean.emailLostPassword business method");
      //Pretend we are looking up password and email, and place a message on the queue
      String password = "xyz";
      String email = "xyz@lalala.com";
      sendMessage(email, "Password Reminder", "Your password is " + password);
      System.out.println("Exiting EmailSystemBean.emailLostPassword business method>");
   }

   @Interceptors({AccountsConfirmInterceptor.class})
   public void sendBookingConfirmationMessage(long orderId)
   {
      System.out.println("<In EmailSystemBean.sendBookingConfirmationMessage business method");
      //Pretend we are looking email, and place a message on the queue
      String email = "xyz@lalala.com";
      sendMessage(email, "Booking Confirmed!", "Your order " + orderId + "is confirmed!");
      System.out.println("Exiting EmailSystemBean.sendBookingConfirmationMessage business method>");
   }
   
   public void sendBookingCancellationMessage(long orderId)
   {
      System.out.println("<In EmailSystemBean.sendBookingCancellationMessage business method");
      //Pretend we are looking email, and place a message on the queue
      String email = "xyz@lalala.com";
      sendMessage(email, "Booking Confirmed!", "Your order " + orderId + "is confirmed!");
      System.out.println("Exiting EmailSystemBean.sendBookingCancellationMessage business method>");
   }

   @ExcludeClassInterceptors
   @ExcludeDefaultInterceptors
   public void noop()
   {
      System.out.println("<In EmailSystemBean.noop business method");
      System.out.println("Exiting EmailSystemBean.noop business method>");
   }

   public void noop2()
   {
      System.out.println("<In EmailSystemBean.noop2 business method");
      System.out.println("Exiting EmailSystemBean.noop2 business method>");
   }

   
   @AroundInvoke
   public Object myBeanInterceptor(InvocationContext ctx) throws Exception
   {
      if (ctx.getMethod().getName().equals("emailLostPassword"))
      {
         System.out.println("*** EmailSystemBean.myBeanInterceptor - username: " + ctx.getParameters()[0]);
      }

      return ctx.proceed();
   }

   private void sendMessage(String email, String subject, String body)
   {
      QueueConnection cnn = null;
      QueueSession session = null;
      try
      {
         QueueSender sender = null;
         InitialContext ctx = new InitialContext();
         Queue queue = (Queue) ctx.lookup("queue/tutorial/email");
         QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
         cnn = factory.createQueueConnection();
         session = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

         TextMessage msg = session.createTextMessage(
               "<mail>" +
               "<to>" + email + "</to>" +
               "<to>" + subject + "</to>" +
               "<msg>" + body +"</msg>" +
               "</mail>");

         sender = session.createSender(queue);
         sender.send(msg);
         System.out.println("Message sent successfully to remote queue.");
      }
      catch(Exception e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         try
         {
            session.close();
            cnn.close();
         }
         catch (JMSException e)
         {
            throw new RuntimeException(e);
         }
      }
   }
}

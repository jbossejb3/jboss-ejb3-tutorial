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
package org.jboss.tutorial.resource_ref.bean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.ejb.Remove;

import org.apache.log4j.Logger;
import org.jboss.ejb3.Container;

/** A bean that does nothing but access resources from the ENC
 to test ENC usage.

 @author Scott.Stark@jboss.org
 @version $Revision: 61136 $
 */
public class TestENCBean implements TestENC
{
   Logger log = Logger.getLogger(getClass());

   public void accessENC() throws NamingException
   {
      // Obtain the enterprise beans environment naming context.
      Context initCtx = new InitialContext();
      Context myEnv = (Context) initCtx.lookup(Container.ENC_CTX_NAME + "/env");
      
      // This bean should have the full ENC setup of the ENCBean
      testJdbcDataSource(initCtx, myEnv);
      testMail(initCtx, myEnv);
      testJMS(initCtx, myEnv);
      testResourceEnvEntries(initCtx, myEnv);
   }

   private void testJdbcDataSource(Context initCtx, Context myEnv) throws NamingException
   {
      // JDBC DataSource
      Object obj = myEnv.lookup("jdbc/DefaultDS");
      if ((obj instanceof javax.sql.DataSource) == false)
         throw new NamingException("jdbc/DefaultDS is not a javax.sql.DataSource");
      log.info("Found data source resource ref");
   }

   private void testMail(Context initCtx, Context myEnv) throws NamingException
   {
      // JavaMail Session
      Object obj = myEnv.lookup("mail/DefaultMail");
      if ((obj instanceof javax.mail.Session) == false)
         throw new NamingException("DefaultMail is not a javax.mail.Session");
      log.info("Found mail resource ref");
   }

   private void testJMS(Context initCtx, Context myEnv) throws NamingException
   {
      // JavaMail Session
      Object obj = myEnv.lookup("jms/QueFactory");
      if ((obj instanceof javax.jms.QueueConnectionFactory) == false)
         throw new NamingException("mail/DefaultMail is not a javax.jms.QueueConnectionFactory");
      log.info("Found jms queue resource ref");
   }

   private void testResourceEnvEntries(Context initCtx, Context myEnv) throws NamingException
   {
      Object obj = myEnv.lookup("res/aQueue");
      if ((obj instanceof javax.jms.Queue) == false)
         throw new NamingException("res/aQueue is not a javax.jms.Queue");
      log.info("Found jms queue resource env ref");
   }
   
   @Remove
   public void remove()
   {
      
   }
}

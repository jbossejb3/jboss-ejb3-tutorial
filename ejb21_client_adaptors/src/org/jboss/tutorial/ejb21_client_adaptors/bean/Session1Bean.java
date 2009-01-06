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
package org.jboss.tutorial.ejb21_client_adaptors.bean;

import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.ejb.Init;
import javax.ejb.RemoteHome;
import javax.ejb.Stateful;
import javax.naming.InitialContext;

import org.jboss.ejb3.Container;
import org.jboss.logging.Logger;

/**
 * @version <tt>$Revision$</tt>
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author Jaikiran Pai
 */
@Stateful(name = "Session1")
@RemoteHome(Session1RemoteHome.class)
@EJBs(
{@EJB(name = "session2", beanInterface = org.jboss.tutorial.ejb21_client_adaptors.bean.Session2LocalHome.class, beanName = "Session2")})
public class Session1Bean
{
   private static final Logger log = Logger.getLogger(Session1Bean.class);

   private String initValue = null;

   public String getInitValue()
   {
      return initValue;
   }

   public String getLocalSession2InitValue() throws Exception
   {
      InitialContext jndiContext = new InitialContext();
      Object s = jndiContext.lookup(Container.ENC_CTX_NAME + "/env/session2");
      Session2LocalHome home = (Session2LocalHome) jndiContext.lookup(Container.ENC_CTX_NAME + "/env/session2");
      Session2Local session2 = home.create("initialized");
      return session2.getInitValue();
   }

   @Init
   public void ejbCreate()
   {
      initValue = "initialized";
      log.info("ejbCreate() of " + this.getClass() + " called");
   }

}

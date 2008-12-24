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
package org.jboss.tutorial.consumer.bean;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.JndiInject;
import org.jboss.ejb3.mdb.ProducerManager;
import org.jboss.ejb3.mdb.ProducerObject;

/**
 * Show injecting in producers
 * Show how to interact with local producers
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision$
 */
@Stateless
@Remote(Tester.class)
public class TesterBean implements Tester
{
   private ExampleProducerXA xa;

   private ProducerManager xaManager;

   @JndiInject(jndiName = "org.jboss.tutorial.consumer.bean.ExampleProducerXA")
   public void setXa(ExampleProducerXA xa)
   {
      this.xa = xa;
      this.xaManager = ((ProducerObject) xa).getProducerManager();
   }

   private ExampleProducer local;

   private ProducerManager localManager;

   @JndiInject(jndiName = "org.jboss.tutorial.consumer.bean.ExampleProducerLocal")
   public void setLocal(ExampleProducer local)
   {
      this.local = local;
      this.localManager = ((ProducerObject) local).getProducerManager();
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void testXA() throws Exception
   {

      xaManager.connect();
      xa.method1("testXA", 1);
      Map<String, String> map = new HashMap<String, String>();
      map.put("hello", "world");
      map.put("great", "ejb3");
      xa.method2("testXA2", map);
      System.out.println("end TESTXA **");
      xaManager.close();
   }

   public void testLocal() throws Exception
   {

      localManager.connect();
      local.method1("testLocal", 1);
      Map<String, String> map = new HashMap<String, String>();
      map.put("hello", "world");
      map.put("great", "ejb3");
      local.method2("testLocal2", map);
      localManager.close();
   }

}

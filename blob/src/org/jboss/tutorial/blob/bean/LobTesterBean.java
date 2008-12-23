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
package org.jboss.tutorial.blob.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContext;
import org.hibernate.Hibernate;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision$
 */
@Stateless
@Remote(LobTester.class)
public class LobTesterBean implements LobTester
{

   @PersistenceContext EntityManager manager;

   public long create()
   {
      BlobEntity blob = new BlobEntity();

      HashMap map = new HashMap();
      map.put("hello", "world");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try
      {
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(map);
         blob.setBlobby(Hibernate.createBlob(baos.toByteArray()));
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }


      String clobby = "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work";
      blob.setClobby(Hibernate.createClob(clobby));
      manager.persist(blob);
      return blob.getId();
   }

   public HashMap findBlob(long id) throws Exception
   {
      BlobEntity blob = manager.find(BlobEntity.class, id);
      ObjectInputStream ois = new ObjectInputStream(blob.getBlobby().getBinaryStream());
      return (HashMap) ois.readObject();
   }

   public String findClob(long id) throws Exception
   {
      BlobEntity blob = manager.find(BlobEntity.class, id);
      return blob.getClobby().getSubString(1, 31);
   }

   public long create2()
   {
      BlobEntity2 blob = new BlobEntity2();

      HashMap map = new HashMap();
      map.put("hello", "world");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try
      {
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(map);
         blob.setBlobby(baos.toByteArray());
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }


      String clobby = "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work" +
      "This is a very long string that will be stored in a java.sql.Clob hopefully.  We'll see how this works and if it will work";
      blob.setClobby(clobby);
      manager.persist(blob);
      return blob.getId();
   }

   public BlobEntity2 findBlob2(long id) throws Exception
   {
      return manager.find(BlobEntity2.class, id);
   }

}

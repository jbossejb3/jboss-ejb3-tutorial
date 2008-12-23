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
package org.jboss.tutorial.blob.client;

import java.util.HashMap;
import javax.naming.InitialContext;
import org.jboss.tutorial.blob.bean.LobTester;
import org.jboss.tutorial.blob.bean.BlobEntity2;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision$
 */
public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      LobTester test = (LobTester) ctx.lookup("LobTesterBean/remote");
      long blobId = test.create();
      HashMap map = test.findBlob(blobId);
      System.out.println("is hello in map: " + map.get("hello"));
      System.out.println(test.findClob(blobId));
      System.out.println("creating and getting a BlobEntity2 that uses byte[] and String instead of Clob/Blob");
      blobId = test.create2();
      BlobEntity2 entity = test.findBlob2(blobId);

   }
}

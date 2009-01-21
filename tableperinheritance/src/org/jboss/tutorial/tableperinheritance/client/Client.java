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
package org.jboss.tutorial.tableperinheritance.client;


import org.jboss.tutorial.tableperinheritance.bean.Pet;
import org.jboss.tutorial.tableperinheritance.bean.PetDAO;

import javax.naming.InitialContext;

import java.util.List;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 61136 $
 */
public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      PetDAO dao = (PetDAO) ctx.lookup("PetDAOBean/remote");

      dao.createCat("Toonses", 15.0, 9);
      dao.createCat("Sox", 10.0, 5);
      dao.createDog("Winnie", 70.0, 5);
      dao.createDog("Junior", 11.0, 1);

      List l = dao.findByWeight(14.0);
      for (Object o : l)
      {
         System.out.println(((Pet) o).getName());
      }
   }
}

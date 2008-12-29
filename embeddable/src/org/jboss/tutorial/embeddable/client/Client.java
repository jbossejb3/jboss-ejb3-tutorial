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
package org.jboss.tutorial.embeddable.client;

import org.jboss.tutorial.embeddable.bean.Customer;
import org.jboss.tutorial.embeddable.bean.CustomerDAO;

import javax.naming.InitialContext;

import java.util.List;


public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      CustomerDAO dao = (CustomerDAO) ctx.lookup("CustomerDAOBean/remote");

      System.out.println("Create Bill Burke and Monica Smith");
      dao.create("Bill", "Burke", "1 Boston Road", "Boston", "MA", "02115");
      int moId = dao.create("Monica", "Smith", "1 Boston Road", "Boston", "MA", "02115");

      System.out.println("Bill and Monica get married");
      Customer monica = dao.find(moId);
      monica.getName().setLast("Burke");
      dao.merge(monica);

      System.out.println("Get all the Burkes");
      List burkes = dao.findByLastName("Burke");
      System.out.println("There are now " + burkes.size() + " Burkes");
   }
}

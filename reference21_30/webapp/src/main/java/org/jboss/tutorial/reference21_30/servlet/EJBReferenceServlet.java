/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.tutorial.reference21_30.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.tutorial.reference21_30.bean.Stateless2;
import org.jboss.tutorial.reference21_30.bean.Stateless2Home;
import org.jboss.tutorial.reference21_30.bean.Stateless3;

/**
 * EJBReferenceServlet
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class EJBReferenceServlet extends HttpServlet
{

   /**
    * Default constructor
    */
   public EJBReferenceServlet()
   {

   }

   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      accessReferences(req, resp);
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      accessReferences(req, resp);
   }
   
   private void accessReferences(HttpServletRequest req, HttpServletResponse resp) 
   {
      try {
         Context ctx = new InitialContext();
         
         System.out.println("Testing EJB3.0 references to EJB2.x");
         Stateless3 test3 = (Stateless3)ctx.lookup("Stateless3");
         test3.testAccess();
         
         System.out.println("Testing EJB2.x references to EJB3.0");
         Stateless2Home home = (Stateless2Home)ctx.lookup("Stateless2");
         Stateless2 test2 = home.create();
         test2.testAccess();
         
         System.out.println("Succeeded");
         
         printMessage(resp, "Successfully referenced EJB3 from EJB2.1 and vice versa");
         
      } catch (Exception e) {

         throw new RuntimeException("ERROR while accessing the referencing EJBs - ",e);
      }
   }
   
   private void printMessage(HttpServletResponse resp, String msg) throws IOException
   {
      resp.setContentType("text/html");
      PrintWriter writer = resp.getWriter();
      writer.println("<html>");
      writer.println("<body>");
      writer.println("<h1> " + msg + "</h1>");
      writer.println("</body");
      writer.println("</html>");
   }
}

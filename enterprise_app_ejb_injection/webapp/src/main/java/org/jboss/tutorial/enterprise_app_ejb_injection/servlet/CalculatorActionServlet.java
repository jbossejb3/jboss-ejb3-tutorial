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
package org.jboss.tutorial.enterprise_app_ejb_injection.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.tutorial.enterprise_app_ejb_injection.bean.CalculatorLocal;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author Jaikiran Pai
 */
public class CalculatorActionServlet extends HttpServlet
{

   private CalculatorLocal calculator;

   /**
    * Injecting the EJB
    */
   @EJB(name = "calculator")
   public void setCalculator(CalculatorLocal calculator)
   {
      this.calculator = calculator;
   }

   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      doit(req, resp);
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      doit(req, resp);
   }

   /**
    * Use the Calculator bean to perform the operation
    * 
    * @param req
    * @param resp
    * @throws ServletException
    * @throws IOException
    */
   protected void doit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      int op1;
      int op2;
      try {
         op1 = Integer.parseInt(req.getParameter("op1"));
         op2 = Integer.parseInt(req.getParameter("op2"));
      } catch (NumberFormatException nfe)
      {
         printMessage(resp, "Only integers are allowed in input");
         return;
      }
      String action = req.getParameter("action");

      System.out.println("Using the injected calculator bean to perform " + action + " on " + op1 + " and " + op2);

      int result = 0;
      if (action.equals("Add"))
         result = calculator.add(op1, op2);
      else if (action.equals("Subtract"))
         result = calculator.subtract(op1, op2);

      System.out.println("Result returned by Calculator bean = " + result);
      
      printMessage(resp, "Answer = " + result);
      
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

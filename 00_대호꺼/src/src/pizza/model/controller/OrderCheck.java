package pizza.model.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderCheck
 */
@WebServlet("/orderCheck.go")
public class OrderCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderCheck() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String pizza = request.getParameter("pizza");
		String[] topping = request.getParameterValues("topping");
		String[] side = request.getParameterValues("side");
		
		System.out.println(pizza);
		for(int i = 0; i<topping.length; i++) {
			System.out.println(topping[i]);
		}
		for(int i = 0; i<side.length; i++) {
			System.out.println(side[i]);
		}
		
		int total = 0;
		
		String pizzaName = "";
		
		String[] pizzaSplit = pizza.split(",");
		pizzaName = pizzaSplit[0];
		total += Integer.parseInt(pizzaSplit[1]);
		

		String toppingNames = "";
		
		for(int i = 0; i < topping.length; i++) {
			String[] toppingSplit = topping[i].split(",");
			String toppingName = toppingSplit[0];
//			toppingNames.concat(toppingName + ", ");
			toppingNames+=toppingName + ", ";
			total += Integer.parseInt(toppingSplit[1]);
		}
		
		String sideNames = "";
		
		for(int i = 0; i < side.length; i++) {
			String[] sideSplit = side[i].split(",");
			String sideName = sideSplit[0];
			sideNames += sideName + ", ";
			total += Integer.parseInt(sideSplit[1]);
		}
		
		System.out.println(pizzaName);
		System.out.println(toppingNames);
		System.out.println(sideNames);
		System.out.println(total);

		request.setAttribute("pizzaName", pizzaName);
		request.setAttribute("toppingNames", toppingNames);
		request.setAttribute("sideNames", sideNames);
		request.setAttribute("total", total);
		
		RequestDispatcher view = request.getRequestDispatcher("views/1-1Pizza/Pizza2.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

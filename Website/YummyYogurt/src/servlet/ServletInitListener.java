package servlet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ServletInitListener
 *
 */
@WebListener
public class ServletInitListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServletInitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("YummyYogurt");
    	sce.getServletContext().setAttribute("emf", factory);
    }
	
}

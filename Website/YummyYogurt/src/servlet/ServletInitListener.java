package servlet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ServletInitListener implements ServletContextListener {

    public ServletInitListener() {}

    public void contextDestroyed(ServletContextEvent sce)  {}

	
    public void contextInitialized(ServletContextEvent contextEvent)  { 
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("YummyYogurt");
    	contextEvent.getServletContext().setAttribute("factory", factory);
    }
	
}

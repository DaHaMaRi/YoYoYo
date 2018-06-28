package listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class InitialisationListener implements ServletContextListener {

    public InitialisationListener() {}

    public void contextDestroyed(final ServletContextEvent sce)  {}

	
    public void contextInitialized(final ServletContextEvent contextEvent)  { 
    	final EntityManagerFactory factory = Persistence.createEntityManagerFactory("YummyYogurt");
    	final ServletContext context = contextEvent.getServletContext();
    	
    	context.setAttribute("factory", factory);
    }
	
}

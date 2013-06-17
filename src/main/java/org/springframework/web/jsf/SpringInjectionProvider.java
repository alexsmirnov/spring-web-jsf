package org.springframework.web.jsf;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.sun.faces.spi.DiscoverableInjectionProvider;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.vendor.WebContainerInjectionProvider;

/**
 * This provider integrates Spring dependency injection with JSF.
 * @author asmirnov
 *
 */
public class SpringInjectionProvider extends DiscoverableInjectionProvider {

    private static final Logger LOG = LoggerFactory
	    .getLogger(SpringInjectionProvider.class);

    private static final InjectionProvider DEFAULT_PROVIDER = new WebContainerInjectionProvider();

    private ServletContext context;

    public SpringInjectionProvider(ServletContext context) {
	this.context = context;
    }

    /*
     * (non-Javadoc) Due to JSF 2.2 bug, invokePostConstruct alwais called
     * before inject.
     * 
     * @see com.sun.faces.spi.InjectionProvider#inject(java.lang.Object)
     */
    @Override
    public void inject(Object managedBean) throws InjectionProviderException {
	// ApplicationContext applicationContext = getApplicationContext();
	// if(null != managedBean && null != applicationContext) {
	// LOG.info("Inject dependencies to JSF object {}",
	// managedBean.getClass().getName());
	// applicationContext.getAutowireCapableBeanFactory().autowireBean(managedBean);
	// }
    }

    @Override
    public void invokePreDestroy(Object managedBean)
	    throws InjectionProviderException {
	ApplicationContext applicationContext = getApplicationContext();
	if (null != managedBean && null != applicationContext) {
	    LOG.info("Destroy JSF object {}", managedBean.getClass().getName());
	    try {
		ConfigurableBeanFactory autowireCapableBeanFactory = (ConfigurableBeanFactory) applicationContext
			.getAutowireCapableBeanFactory();
		autowireCapableBeanFactory.destroyBean(managedBean.getClass()
			.getSimpleName(), managedBean);
	    } catch (NoSuchBeanDefinitionException e) {
		LOG.warn("Cannot destroy Spring bean {}, invoke @PreDestroy",
			e.getBeanName());
		DEFAULT_PROVIDER.invokePreDestroy(managedBean);
	    }
	}
    }

    @Override
    public void invokePostConstruct(Object managedBean)
	    throws InjectionProviderException {
	ApplicationContext applicationContext = getApplicationContext();
	if (null != managedBean && null != applicationContext) {
	    LOG.info("Do post processing on JSF object {}", managedBean
		    .getClass().getName());
	    try {
		applicationContext.getAutowireCapableBeanFactory()
			.autowireBean(managedBean);
		applicationContext.getAutowireCapableBeanFactory()
			.initializeBean(managedBean,
				managedBean.getClass().getSimpleName());
	    } catch (BeansException e) {
		throw e;
	    }
	}
    }

    ApplicationContext getApplicationContext() {
	Object attribute = context
		.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	if (attribute instanceof ApplicationContext) {
	    ApplicationContext appContext = (ApplicationContext) attribute;
	    return appContext;
	} else {
	    return null;
	}
    }
}

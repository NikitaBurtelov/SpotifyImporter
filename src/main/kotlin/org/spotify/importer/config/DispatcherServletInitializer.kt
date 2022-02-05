package org.spotify.importer.config

import org.springframework.web.filter.HiddenHttpMethodFilter
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer
import javax.servlet.ServletContext
import javax.servlet.ServletException

/**
 * @author Nikita Burtelov
 * Замена web.xml
 */
class DispatcherServletInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<Class<*>>? {
        return null
    }

    override fun getServletConfigClasses(): Array<Class<*>> {
        return arrayOfSpringConfig::class.java)
    }

    override fun getServletMappings(): Array<String> {
        return arrayOf("/")
    }

    @Throws(ServletException::class)
    override fun onStartup(aServletContext: ServletContext) {
        super.onStartup(aServletContext)
        registerHiddenFieldFilter(aServletContext)
    }

    private fun registerHiddenFieldFilter(aContext: ServletContext) {
        aContext.addFilter(
            "hiddenHttpMethodFilter",
            HiddenHttpMethodFilter()
        ).addMappingForUrlPatterns(null, true, "/*")
    }
}
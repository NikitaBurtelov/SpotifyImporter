package org.burtelov.spotifyimporter.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author Nikita Burtelov
 */
@Configuration
@EnableWebMvc
class SpringConfig @Autowired constructor(private val applicationContext: ApplicationContext) :
    WebMvcConfigurer {
    @Bean
    fun templateResolver(): SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver()
        templateResolver.setApplicationContext(applicationContext)
        templateResolver.setPrefix("/WEB-INF/views/")
        templateResolver.setSuffix(".html")
        return templateResolver
    }

    @Bean
    fun templateResolverCss(): SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver()
        templateResolver.setApplicationContext(applicationContext)
        templateResolver.setPrefix("/WEB-INF/views/css")
        templateResolver.setSuffix(".css")
        return templateResolver
    }

    @Bean
    fun templateEngine(): SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.addTemplateResolver(templateResolverCss())
        templateEngine.setTemplateResolver(templateResolver())
        templateEngine.setEnableSpringELCompiler(true)
        return templateEngine
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("/WEB-INF/views/css")
    }

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        val resolver = ThymeleafViewResolver()
        resolver.setTemplateEngine(templateEngine())
        registry.viewResolver(resolver)
    }
}
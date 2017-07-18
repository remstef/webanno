/*
 * Copyright 2017
 * Ubiquitous Knowledge Processing (UKP) Lab and FG Language Technology
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.webapp;

import javax.validation.Validator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import de.tudarmstadt.ukp.clarin.webanno.webapp.config.WebAnnoApplicationContextInitializer;
import de.tudarmstadt.ukp.clarin.webanno.webapp.config.WebAnnoBanner;

/**
 * Boots WebAnno in standalone JAR or WAR modes.
 */
@SpringBootApplication
@ImportResource({ 
        "classpath:/META-INF/application-context.xml",
        "classpath:/META-INF/rest-context.xml", 
        "classpath:/META-INF/database-context.xml",
        "classpath:/META-INF/static-resources-context.xml" })
public class WebAnno
    extends SpringBootServletInitializer
{
    @Bean
    @Primary
    public Validator validator()
    {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer()
    {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        return tomcat;
    }

    @Override
    protected SpringApplicationBuilder createSpringApplicationBuilder()
    {
        SpringApplicationBuilder builder = super.createSpringApplicationBuilder();
        builder.properties("running.from.commandline=false");
        init(builder);
        return builder;
    }
    
    private static void init(SpringApplicationBuilder aBuilder)
    {
        aBuilder.banner(new WebAnnoBanner());
        aBuilder.initializers(new WebAnnoApplicationContextInitializer());
    }
    
    public static void main(String[] args) throws Exception
    {
        // If running from the command line, we default do assuming that there is a head
        if (System.getProperty("java.awt.headless") == null) {
            System.setProperty("java.awt.headless", "false");
        }
        
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        // Signal that we may need the shutdown dialog
        builder.properties("running.from.commandline=true");
        init(builder);
        builder.sources(WebAnno.class);
        builder.run(args);
    }
}
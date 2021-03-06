package io.github.mklew.geda.autodiscovery;

import java.util.Set;

import org.fest.assertions.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import io.github.mklew.geda.autodiscovery.domain.impl.PersonImpl;
import io.github.mklew.geda.autodiscovery.internal.RegisterAsAnnotationScannerImpl;


/**
 * @author Marek Lewandowski
 * @since 7/27/13
 */
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RegisterAsAnnotationScannerTest extends AbstractTestNGSpringContextTests
{
    @Configuration
    public static class Config {

        @Bean
        public RegisterAsAnnotationScanner registerAsAnnotationScanner()
        {
            return new RegisterAsAnnotationScannerImpl("io.github.mklew.geda.autodiscovery.domain");
        }

    }

    @Autowired
    RegisterAsAnnotationScanner registerAsAnnotationScanner;

    @Test
    public void should_discover_entity()
    {
        // when
        registerAsAnnotationScanner.scan();
        Set<Class<?>> classes = registerAsAnnotationScanner.getClassesAnnotatedWithRegisterAs();

        // then
        Assertions.assertThat(classes).isNotEmpty().hasSize(1).contains(PersonImpl.class);
    }

}

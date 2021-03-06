package io.github.mklew.geda.autodiscovery;

import com.inspiresoftware.lib.dto.geda.DTOFactory;
import io.github.mklew.geda.autodiscovery.annotations.RegisterAs;
import io.github.mklew.geda.autodiscovery.annotations.RepresentAs;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author Marek Lewandowski
 * @since 7/27/13
 */
public class DtoEntityRegistrar
{
    @Autowired
    DTOFactory dtoFactory;

    @Autowired
    RegisterAsSplitter splitter;

    @Autowired
    RegisterAsAnnotationScanner registerAsAnnotationScanner;

    @PostConstruct
    public void registerDtoAndEntites()
    {
        registerAsAnnotationScanner.scan();
        final Set<Class<?>> classesAnnotatedWithRegisterAs = registerAsAnnotationScanner.getClassesAnnotatedWithRegisterAs();
        final DEA dea = splitter.split(classesAnnotatedWithRegisterAs);

        for(Class<?> clazz : dea.getDtos())
        {
            final RegisterAs annotation = clazz.getAnnotation(RegisterAs.class);
            dtoFactory.registerDto(annotation.value(), clazz.getName());
        }

        for(Class<?> clazz : dea.getEntities())
        {
            Class<?> representAs = null;
            if (clazz.isAnnotationPresent(RepresentAs.class))
                representAs = clazz.getAnnotation(RepresentAs.class).value();
            else
                representAs = clazz;
            dtoFactory.registerEntity(clazz.getAnnotation(RegisterAs.class).value(), clazz.getName(), representAs.getCanonicalName());
        }
    }

    public void setDtoFactory(DTOFactory dtoFactory)
    {
        this.dtoFactory = dtoFactory;
    }

    public void setSplitter(RegisterAsSplitter splitter)
    {
        this.splitter = splitter;
    }

    public void setRegisterAsAnnotationScanner(RegisterAsAnnotationScanner registerAsAnnotationScanner)
    {
        this.registerAsAnnotationScanner = registerAsAnnotationScanner;
    }
}

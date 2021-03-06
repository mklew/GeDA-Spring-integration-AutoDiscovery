package io.github.mklew.geda.autodiscovery;

import com.inspiresoftware.lib.dto.geda.impl.DTOFactoryImpl;
import io.github.mklew.geda.autodiscovery.internal.RegisterAsSplitterImpl;
import io.github.mklew.geda.autodiscovery.internal.RegisterAsAnnotationScannerImpl;

import javax.annotation.PostConstruct;

/**
 * @author Marek Lewandowski
 * @since 7/27/13
 */
public class AutoDiscoveryDTOFactoryImpl extends DTOFactoryImpl
{
    private final String[] basePackages;

    public AutoDiscoveryDTOFactoryImpl(String... basePackages)
    {
        this.basePackages = basePackages;
    }

    @PostConstruct
    void registerDtosAndEntities()
    {
        RegisterAsSplitter splitter = new RegisterAsSplitterImpl();
        RegisterAsAnnotationScanner scanner = new RegisterAsAnnotationScannerImpl(basePackages);
        DtoEntityRegistrar registrar = new DtoEntityRegistrar();

        registrar.setDtoFactory(this);
        registrar.setRegisterAsAnnotationScanner(scanner);
        registrar.setSplitter(splitter);
        registrar.registerDtoAndEntites();
    }
}

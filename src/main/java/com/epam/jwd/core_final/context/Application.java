package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.CashRefresher;
import com.epam.jwd.core_final.util.EntityIdGenerator;

import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        final NassaContext nassaContext = NassaContext.getInstance();
        final Supplier<ApplicationContext> applicationContextSupplier = () -> nassaContext;
        EntityIdGenerator.resetAll();
        nassaContext.init();
        return applicationContextSupplier::get;
    }

    static void startRefreshing() {
        CashRefresher.getInstance().start();
    }
}

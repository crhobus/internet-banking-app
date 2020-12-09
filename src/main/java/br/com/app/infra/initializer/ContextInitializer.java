package br.com.app.infra.initializer;

import br.com.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    /*
     * Executa ao iniciar a aplicação
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        userService.createDefaultUser();
    }

}

package hookah_sql.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Context {
    private static AnnotationConfigApplicationContext context;
    private static Context instance;
    private Context () {
        context = new AnnotationConfigApplicationContext(SpringConfig.class);
    };

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }
    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public void close() {
        context.close();
    }

}

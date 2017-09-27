package payments.stone.com.br.desafiomobile.commons;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class Bus {
    public static Bus instance;

    public static Bus getInstance(){
        if(instance == null){
            instance = new Bus();
        }
        return instance;
    }

    public void register() {

    }

    public void unregister() {

    }

    public void post() {

    }
}

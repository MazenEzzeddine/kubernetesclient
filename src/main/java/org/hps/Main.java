package org.hps;


import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Inside Main");

        KubernetesClient client = new DefaultKubernetesClient();

        Watcherr watcherr = new Watcherr(client);

        watcherr.startWatchingDeployment();
        watcherr.startWatchingDeployment();
        watcherr.startWatchingPod();

        MyDeployment deploy = new MyDeployment(client);

        deploy.createDeployment();


    }

}


package org.hps;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Watcherr {

    private KubernetesClient client;

    private static final Logger logger = LogManager.getLogger(Watcherr.class);



    public Watcherr(KubernetesClient client) {
        this.client = client;
    }


    public void startWatchingDeployment() {
        client.apps().deployments().inAnyNamespace().watch(new Watcher<Deployment>() {


            @Override
            public void eventReceived(Action action, Deployment deployment) {
                logger.info("Deployment eventReceived >>>>> "+action + " > " + deployment.getMetadata().getName());
            }

            @Override
            public void onClose(WatcherException e) {

            }
        });
    }

    public void startWatchingPod() {
        client.pods().inAnyNamespace().watch(new Watcher<Pod>() {
            @Override
            public void eventReceived(Action action, Pod pod) {

                logger.info("Pod   eventReceived >>>>> "+action + " > " + pod.getMetadata().getName());
            }

            @Override
            public void onClose(WatcherException e) {

            }
        });

    }

}

package org.hps;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class MyPod {
    private static final Logger logger = LogManager.getLogger(MyPod.class);
    private KubernetesClient client;


    public MyPod(KubernetesClient client) {
        this.client = client;
    }

    void createPod() throws InterruptedException {
        // list pods in the default namespace
        PodList pods = client.pods().inNamespace("default").list();
        pods.getItems().stream().forEach(s -> logger.info("Found pod: " + s.getMetadata().getName()));

        // create a pod
        logger.info("Creating a pod");
        Pod pod = client.pods().inNamespace("default").create(new PodBuilder()
                .withNewMetadata()
                .withName("my-programmatically-created-pod")
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName("main")
                .withImage("busybox")
                .withCommand(Arrays.asList("sleep", "99999"))
                .endContainer()
                .endSpec()
                .build());
        logger.info("Created pod: " + pod);

        // edit the pod (add a label to it)
        client.pods().inNamespace("default").withName("my-programmatically-created-pod").edit(p -> new PodBuilder(p)
                .editMetadata()
                .addToLabels("foo", "bar")
                .and().build());

        logger.info("Added label foo=bar to pod");

        System.out.println("Waiting 1 minute before deleting pod...");
        Thread.sleep(60000);

        // delete the pod
        client.pods().inNamespace("default").withName("my-programmatically-created-pod").delete();
        logger.info("Deleted the pod");

    }
}

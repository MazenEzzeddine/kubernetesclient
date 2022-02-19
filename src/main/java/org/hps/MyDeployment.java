package org.hps;


import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyDeployment {

    private static final Logger logger = LogManager.getLogger(MyDeployment.class);
    private KubernetesClient client;


    public MyDeployment(KubernetesClient client) {
        this.client = client;
    }

    public void createDeployment() throws InterruptedException {

        logger.info("creating the deployment");
        Deployment deployment1 = new DeploymentBuilder()
                .withNewMetadata()
                .withName("deployment1")
                .addToLabels("test", "deployment")
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", "httpd")
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName("busybox")
                .withImage("busybox")
                .withCommand("sleep", "360000")
                .endContainer()
                .endSpec()
                .endTemplate()
                .withNewSelector()
                .addToMatchLabels("app", "httpd")
                .endSelector()
                .endSpec()
                .build();

        client.apps().deployments().inNamespace("default").create(deployment1);


        logger.info("deployment created you might want to");


        logger.info("OK, sleeping for 1 min and then scaling the deployment");


        Thread.sleep(60000);


        logger.info("Currently we have this number of replicas for deployment 1 {}", deployment1.getSpec().getReplicas());


        logger.info("scaling deployment for deployment1 by 2");

        client.apps().deployments().inNamespace("default").withName("deployment1").scale(
                deployment1.getSpec().getReplicas() + 2);
    }


    private void watchEvents() {

    }


}

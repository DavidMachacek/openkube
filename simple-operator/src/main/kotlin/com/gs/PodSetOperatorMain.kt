package com.gs

import com.gs.crd.DoneablePodSet
import com.gs.crd.PodSet
import com.gs.crd.PodSetList
import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.api.model.PodList
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinitionBuilder
import io.fabric8.kubernetes.client.DefaultKubernetesClient
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext
import java.util.logging.Logger

class PodSetOperatorMain {

    companion object {
        val logger = Logger.getLogger(PodSetOperatorMain::class.java.name)

        @JvmStatic
        fun main(args: Array<String>) {
            val client = DefaultKubernetesClient()
            logger.info("All namespaces ${client.namespaces().list()}")
            logger.info("All services ${client.services().list()}")
            val namespace = client.namespace ?: "default".also { logger.info("namespace is null, setting to default") }
            logger.info("masterUrl: ${client.masterUrl}")
            logger.info("using namespace $namespace")
            val podSetCrdDefinition: CustomResourceDefinition = CustomResourceDefinitionBuilder()
                    .withNewMetadata().withName("podsets.david.k8s.io").endMetadata()
                    .withNewSpec()
                    .withGroup("david.k8s.io")
                    .withVersion("v1alpha1")
                    .withNewNames().withKind("PodSet").withPlural("podsets").endNames()
                    .withScope("Namespaced")
                    .endSpec()
                    .build()
            val podSetCrdContext = CustomResourceDefinitionContext.Builder()
                    .withVersion("v1alpha1")
                    .withScope("Namespaced")
                    .withGroup("david.k8s.io")
                    .withPlural("podsets")
                    .build()

            val informerFactory = client.informers()
            val podSetClient = client.customResources(podSetCrdDefinition, PodSet::class.java, PodSetList::class.java, DoneablePodSet::class.java)
            val podSharedIndexInformer = informerFactory.sharedIndexInformerFor(Pod::class.java, PodList::class.java, 10 * 60 * 1000)
            val podSetSharedIndexInformer = informerFactory.sharedIndexInformerForCustomResource(podSetCrdContext, PodSet::class.java, PodSetList::class.java, 10 * 60 * 1000)
            val podSetController = PodSetController(client, podSharedIndexInformer, podSetSharedIndexInformer, namespace)

            podSetController.create()
            informerFactory.startAllRegisteredInformers()

            podSetController.run()
        }
    }
}
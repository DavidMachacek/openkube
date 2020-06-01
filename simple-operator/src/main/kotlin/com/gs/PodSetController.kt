package com.gs

import com.gs.crd.PodSet
import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.api.model.PodBuilder
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.informers.ResourceEventHandler
import io.fabric8.kubernetes.client.informers.SharedIndexInformer
import io.fabric8.kubernetes.client.informers.cache.Cache
import io.fabric8.kubernetes.client.informers.cache.Lister
import java.util.*
import java.util.AbstractMap.SimpleEntry
import java.util.concurrent.ArrayBlockingQueue
import java.util.logging.Logger


class PodSetController(
        val client: KubernetesClient,
        val podInformer: SharedIndexInformer<Pod>,
        val podSetInformer: SharedIndexInformer<PodSet>,
        val namespace: String
) {

    val workqueue = ArrayBlockingQueue<List<String>>(1024)
    val podSetLister = Lister<PodSet>(podSetInformer.indexer, namespace)
    val podLister = Lister<Pod>(podInformer.indexer, namespace)

    companion object {
        val logger = Logger.getLogger(PodSetController::class.java.name)
        const val APP_LABEL = "app"
    }

    fun create() {
        logger.info("Creating podSetInformer.addEventHandler")
        podSetInformer.addEventHandler(object : ResourceEventHandler<PodSet> {
            override fun onUpdate(oldObj: PodSet, newObj: PodSet) {
                logger.info("Calling PodSet onUpdate old - ${oldObj.metadata.namespace}/${oldObj.metadata.name}, new - ${oldObj.metadata.namespace}/${oldObj.metadata.name}")
                //enquePodPodSet(newObj)
            }

            override fun onDelete(obj: PodSet, deletedFinalStateUnknown: Boolean) {
                logger.info("Calling PodSet onDelete - ${obj.metadata.namespace}/${obj.metadata.name}")
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAdd(obj: PodSet) {
                logger.info("Calling PodSet onAdd - ${obj.metadata.namespace}/${obj.metadata.name}")
                //enquePodPodSet(obj)
            }
        })
        /*
        logger.info("Creating podInformer.addEventHandler")
        podInformer.addEventHandler(object : ResourceEventHandler<Pod> {
            override fun onUpdate(oldObj: Pod, newObj: Pod) {
                logger.info("Calling Pod onUpdate - ${oldObj.metadata.namespace}/${oldObj.metadata.name}, new - ${newObj.metadata.namespace}/${newObj.metadata.name}")
                //enquePodPodSet(newObj)
            }

            override fun onDelete(obj: Pod, deletedFinalStateUnknown: Boolean) {
                logger.info("Calling Pod onDelete - ${obj.metadata.namespace}/${obj.metadata.name}")
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAdd(obj: Pod) {
                logger.info("Calling Pod onAdd - ${obj.metadata.namespace}/${obj.metadata.name}")
                //enquePodPodSet(obj)
            }
        })*/
    }

    fun run() {
        logger.info("Starting Pod Controller")
        while (!podSetInformer.hasSynced())
        while (true) {
            logger.info("trying to fetch item from workqueue")
            if (workqueue.isEmpty()) {
                logger.info("Work Queue is empty")
            }
            val currentKey = workqueue.take()
            currentKey.forEach {
                logger.info("$it found!")
            }
            if (currentKey == null || currentKey.isEmpty() || (!currentKey.contains("/"))) {
                logger.info("invalid resource key: $currentKey")
            }


            // Get the PodSet resource's name from key which is in format namespace/name
            val name = currentKey[0].split("/")[1]
            val podSet = podSetLister.get(name)
            if (podSet == null) {
                logger.warning("PodSet $name in workqueue no longer exists")
                return
            }
            reconcile(podSet)
        }
    }

    /**
     * Tries to achieve the desired state for podset.
     *
     * @param podSet specified podset
     */
    fun reconcile(podSet: PodSet) {
        val pods = podCountByLabel(APP_LABEL, podSet.metadata.name)

        if (pods == null || pods.size == 0) {
            createPods(podSet.podSetSpec.replicas, podSet)
            return
        }
        val existingPods = pods.size

        // Compare it with desired state i.e spec.replicas
        // if less then spin up pods
        // Compare it with desired state i.e spec.replicas
        // if less then spin up pods
        if (existingPods < 4) {
            createPods(podSet.podSetSpec.replicas - existingPods, podSet)
        }

        // If more pods then delete the pods
        // If more pods then delete the pods
        /*
        var diff: Int = existingPods - podSet.podSetSpec.getReplicas()
        while (diff > 0) {
            val podName: String = pods.removeAt(0)
            client.pods().inNamespace(podSet.metadata.namespace).withName(podName).delete()
            diff--
        }*/
    }

    fun createPods(numberOfPods: Int, podSet: PodSet) {
        for (index in 0 until numberOfPods) {
            val pod: Pod = createNewPod(podSet)
            client.pods().inNamespace(podSet.metadata.namespace).create(pod)
        }
    }

    fun createNewPod(podSet: PodSet): Pod {
        logger.info("creating pod ${podSet.metadata.name}")
        return PodBuilder()
                .withNewMetadata()
                .withGenerateName(podSet.metadata.name + "-pod")
                .withNamespace(podSet.getMetadata().getNamespace())
                .withLabels(Collections.singletonMap(APP_LABEL, podSet.getMetadata().getName()))
                .addNewOwnerReference().withController(true).withKind("PodSet").withApiVersion("demo.k8s.io/v1alpha1").withName(podSet.getMetadata().getName()).withNewUid(podSet.getMetadata().getUid()).endOwnerReference()
                .endMetadata()
                .withNewSpec()
                .addNewContainer().withName("busybox").withImage("busybox").withCommand("sleep", "3600").endContainer()
                .endSpec()
                .build()
    }

    fun enquePodPodSet(podSet: PodSet) {
        logger.info("enqueue PodSet ${podSet.metadata.name} from ${podSet.metadata.namespace}")
        val key = Cache.metaNamespaceIndexFunc(podSet)
        logger.info("going to enqueue PodSet $key")
        key?.let {
            logger.info("PodSet $key added")
            workqueue.add(key)
        }
    }

    fun enquePodPodSet(pod: Pod) {
        logger.info("enqueue Pod ${pod.metadata.name} from ${pod.metadata.namespace}")
        val key = Cache.metaNamespaceIndexFunc(pod)
        logger.info("going to enqueue Pod $key")
        key?.let {
            logger.info("Pod $key added")
            workqueue.add(key)
        }
    }

    private fun podCountByLabel(label: String, podSetName: String): List<String> {
        val podNames: MutableList<String> = ArrayList()
        val pods: List<Pod> = podLister.list()
        for (pod in pods) {
            if (pod.metadata.labels.entries.contains(SimpleEntry(label, podSetName))) {
                if (pod.status.phase == "Running" || pod.status.phase == "Pending") {
                    podNames.add(pod.metadata.name)
                }
            }
        }
        logger.info("count: " + podNames.size)
        return podNames
    }
}